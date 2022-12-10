package com.creditcard.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditcard.constants.ApplicationConstants;
import com.creditcard.dto.TransactionDTO;
import com.creditcard.entity.AccountDetails;
import com.creditcard.entity.CashAdvanceChargesCustomized;
import com.creditcard.entity.IAccountDetailsCustomized;
import com.creditcard.entity.LatePaymentCustomized;
import com.creditcard.entity.StatementDetails;
import com.creditcard.entity.StatementDetailsCustomized;
import com.creditcard.entity.TaxPercentageCustomized;
import com.creditcard.entity.TransactionDeatailsCustomized;
import com.creditcard.repository.AccountDetailsRepository;
import com.creditcard.repository.CashAdvanceRepository;
import com.creditcard.repository.CreditCardDetailsRepository;
import com.creditcard.repository.LatePaymentChargeRepository;
import com.creditcard.repository.StatementDetailsRepository;
import com.creditcard.repository.TransactionDetailsRepository;
import com.creditcard.response.Status;
import com.creditcard.service.StatementGenerationService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class StatementGenerationServiceImpl implements StatementGenerationService {

	@Autowired
	TransactionDetailsRepository transactionDetailsRepository;

	@Autowired
	AccountDetailsRepository accountDetailsRepository;

	@Autowired
	CashAdvanceRepository cashAdvanceRepository;

	@Autowired
	LatePaymentChargeRepository latePaymentChargeRepository;

	@Autowired
	CreditCardDetailsRepository creditCardDetailsRepository;

	@Autowired
	StatementDetailsRepository statementDetailsRepository;

	/**
	 * calculation for debit and credit for particulatr account
	 * 
	 * @param accountId
	 * @return
	 */
	private TransactionDTO calculateTransactionAmount(Integer accountId) {
		List<TransactionDeatailsCustomized> transactionDetails = transactionDetailsRepository
				.fetchTransactions(accountId);
		Double credit = ApplicationConstants.INITIAL_CREDIT;
		Double debit = ApplicationConstants.INITIAL_DEBIT;
		for (TransactionDeatailsCustomized transaction : transactionDetails) {
			if (transaction.getTransactionCode().equalsIgnoreCase(ApplicationConstants.CREDIT_TRANSACTION_CODE)) {
				credit = credit + transaction.getTransactionAmount();
			} else if (transaction.getTransactionCode().equalsIgnoreCase(ApplicationConstants.DEBIT_TRANSACTION_CODE)) {
				debit = debit + transaction.getTransactionAmount();
			}
		}
		return new TransactionDTO(credit, debit);
	}

	/**
	 * calculate late payment charge
	 * 
	 * @param accountId
	 * @return
	 */
	private Double calculateLatePayment(Integer accountId) {
		// fetch last bill amount for particular account
		IAccountDetailsCustomized lastBillAmount = accountDetailsRepository.fetchLastBillAmount(accountId);
		Double totalCredit = ApplicationConstants.INITIAL_TOTAL_CREDIT;
		Double latePaymentCharge = ApplicationConstants.INITIAL_LATE_PAYMENT_CHARGE;
		if (lastBillAmount.getLastBillAmount() > ApplicationConstants.ZERO) {
			List<LatePaymentCustomized> transactionDetails = transactionDetailsRepository
					.fetchLatePaymentTransaction(accountId);
			Double minimumRepaymentFee = creditCardDetailsRepository.fetchMinimumRepayment(accountId);
			for (LatePaymentCustomized transaction : transactionDetails) {
				totalCredit = (transaction.getTransactionCode()
						.equalsIgnoreCase(ApplicationConstants.CREDIT_TRANSACTION_CODE))
								? totalCredit + transaction.getTransactionAmount()
								: ApplicationConstants.ZERO;
			}

			latePaymentCharge = totalCredit > minimumRepaymentFee ? ApplicationConstants.ZERO
					: (totalCredit < lastBillAmount.getLastBillAmount()
							? latePaymentChargeRepository.fetchLatePaymentCharge(lastBillAmount.getLastBillAmount())
							: ApplicationConstants.ZERO);

		}
		return latePaymentCharge;

	}

	/**
	 * calculate tax for particular account
	 * 
	 * @param accountId
	 * @param cashAdvanceFee
	 * @return
	 */
	private Double taxCalculation(Integer accountId, Double cashAdvanceFee, Double latePaymentCharge) {
		TaxPercentageCustomized taxPercentageCustomized = creditCardDetailsRepository.fetchTaxPercentage(accountId);
		return (taxPercentageCustomized.getTaxPercentage() / ApplicationConstants.HUNDRED) * cashAdvanceFee
				+ latePaymentCharge;
	}

	/**
	 * generate statement for particular account
	 */
	public Status generatingStatement() {
		Status status = new Status(ApplicationConstants.INSERTION_FAILED, ApplicationConstants.DOWNLOAD_FAILED);
		// fetch account for statement generation
		List<IAccountDetailsCustomized> filteredAccountDeatils = accountDetailsRepository.filteredAccountDetails();
		for (IAccountDetailsCustomized filteredAccount : filteredAccountDeatils) {
			System.out.println("-----------------------------------------------------");
			System.out.println("accountId:" + filteredAccount.getAccountId());
			// calculation for debit and credit for particulatr account
			TransactionDTO transactionDTO = calculateTransactionAmount(filteredAccount.getAccountId());
			System.out.println("credit:" + transactionDTO.getCerdit() + "debit:" + transactionDTO.getDebit());
			// fetch the sum of cash advance fee for particular account
			CashAdvanceChargesCustomized cashAdvanceCharges = cashAdvanceRepository
					.fetchCashAdvanceCharge(filteredAccount.getAccountId());
			Double cashAdvanceFee = (cashAdvanceCharges.getCashAdvanceFee() == null) ? ApplicationConstants.ZERO
					: cashAdvanceCharges.getCashAdvanceFee();
			System.out.println("cash advance fee:" + cashAdvanceFee);
			// latepayment charge for particular account
			Double latePaymentCharge = calculateLatePayment(filteredAccount.getAccountId());
			System.out.println("latePaymentCharge:" + latePaymentCharge);
			// calculate tax for particular account
			Double tax = taxCalculation(filteredAccount.getAccountId(), cashAdvanceFee, latePaymentCharge);
			System.out.println("tax:" + tax);
			// calculate total bill amount for particular account
			Double totalBillAmount = filteredAccount.getLastBillAmount() - transactionDTO.getCerdit()
					+ transactionDTO.getDebit() + cashAdvanceFee + latePaymentCharge + tax;
			System.out.println("Total Bill Amount:" + totalBillAmount);
			AccountDetails accountDetails = new AccountDetails();
			accountDetails.setAccountId(filteredAccount.getAccountId());
			StatementDetails statementDetails = new StatementDetails(totalBillAmount, ApplicationConstants.ZERO, tax,
					latePaymentCharge, accountDetails);
			// save statement details in statement entity
			StatementDetails StatementDetails = statementDetailsRepository.save(statementDetails);
			accountDetailsRepository.updateAccountDetails(totalBillAmount, filteredAccount.getAccountId());
			status = (StatementDetails != null)
					? new Status(ApplicationConstants.INSERTED_SUCCESSFULLY, filteredAccount.getAccountId())
					: new Status(ApplicationConstants.INSERTION_FAILED, filteredAccount.getAccountId(),
							ApplicationConstants.DOWNLOAD_FAILED);
			try {
				pdfGeneration(filteredAccount.getAccountId(), cashAdvanceFee, latePaymentCharge, tax, totalBillAmount);
				status.setFile(ApplicationConstants.DOWNLOAD_SUCCESSFULLY);
			} catch (FileNotFoundException fileNotFoundException) {
				status.setFile(ApplicationConstants.DOWNLOAD_FAILED);
			} catch (DocumentException documentException) {
				status.setFile(ApplicationConstants.DOWNLOAD_FAILED);
			}
		}
		return status;
	}

	public void pdfGeneration(Integer accountId, Double cashAdvanceFee, Double latePaymentFee, Double tax,
			Double totalBillAmont) throws FileNotFoundException, DocumentException {
		StatementDetailsCustomized statementDetails = accountDetailsRepository.statementDetails(accountId);
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(ApplicationConstants.FILE_LOCATION
					+ statementDetails.getCustomerName() + ApplicationConstants.FILE_NAME));
			document.open();
			Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, BaseColor.BLACK);
			String details = "\tCustomer Name:  " + statementDetails.getCustomerName() + "\nCard Number:  "
					+ statementDetails.getCardNumber() + "\nCredit Limit:  " + statementDetails.getCreditCardLimit()
					+ "\nAvailable CreditLimit:  " + statementDetails.getAvailableCreditLimit() + "\nStatement Date:  "
					+ statementDetails.getStatementDate() + "\nPayment Date:  " + statementDetails.getPaymentDate()
					+ "\nCash Advance Fee:  " + cashAdvanceFee + "\nLate Payment Fee:  " + latePaymentFee + "\nTax:  "
					+ tax + "\nTotal Bill Amount:  " + totalBillAmont;
			Paragraph para = new Paragraph(details, font);
			Font headingFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 20, BaseColor.BLACK);
			Paragraph paragraph = new Paragraph(ApplicationConstants.STATEMENT, headingFont);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph);
			document.add(para);
			document.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
