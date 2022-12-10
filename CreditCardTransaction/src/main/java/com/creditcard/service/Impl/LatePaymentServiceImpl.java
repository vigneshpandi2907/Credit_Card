package com.creditcard.service.Impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditcard.constants.ApplicationConstants;
import com.creditcard.constants.ExceptionConstants;
import com.creditcard.entity.AccountDetails;
import com.creditcard.entity.ITransactionCustomized;
import com.creditcard.entity.LateInterest;
import com.creditcard.entity.TransactionDetails;
import com.creditcard.exception.BusinessException;
import com.creditcard.repositrory.IAccountDetailsRepository;
import com.creditcard.repositrory.ILateInterestRepository;
import com.creditcard.repositrory.ITransactionDetailsRepository;
import com.creditcard.response.Status;
import com.creditcard.service.ILatePaymentService;

@Service
public class LatePaymentServiceImpl implements ILatePaymentService {

	@Autowired
	IAccountDetailsRepository accountDetailsRepository;

	@Autowired
	ILateInterestRepository lateInterestRepository;

	@Autowired
	ITransactionDetailsRepository transactionDetailsRepository;

	private Date fromDate;
	private Double interestAmount;
	private Double balanceAmount;
	private Integer lateInterestId;
	private Double availableCreditLimit;
	private Date nextDate;
	private Double cashAmount;
	private Date transactionDate;
	private Boolean cashStatus = ApplicationConstants.FALSE;
	private Double totalCashAmount = ApplicationConstants.ZERO;
	private SimpleDateFormat dateFormat = new SimpleDateFormat(ApplicationConstants.DATE_FORMAT);
	private Status status;

	/**
	 * calculate interest for before payment due date.
	 */
	@Override
	@Transactional
	public Status calculateInterestBeforePaymentDueDate() {
		try {
			List<AccountDetails> accountDetailsList = accountDetailsRepository
					.fetchAccountDetails(ApplicationConstants.NOT_PAID);
			if (!accountDetailsList.isEmpty()) {
				accountDetailsList.stream().distinct().collect(Collectors.toList()).forEach(accountDetails -> {
					calculatePurchaseAmount(accountDetails);
					fromDate = accountDetails.getStatementDate();
					availableCreditLimit = accountDetails.getAvailableCreditLimit();
					interestAmount = ApplicationConstants.ZERO;
					calculateCreditTransactionsBeforeDueDate(accountDetails);
					calculateDebitTransactionsBeforeDueDate(accountDetails);
					availableCreditLimit -= interestAmount;
					updateLateInterestDetails(accountDetails);
					accountDetailsRepository.updateAccountDetails(
							calculateNextMonthOfGivenDate(dateFormat.format(accountDetails.getStatementDate())),
							calculateNextMonthOfGivenDate(dateFormat.format(accountDetails.getPaymentDate())),
							accountDetails.getStatementDate(), availableCreditLimit, accountDetails.getAccountId());
				});
				status = new Status(ApplicationConstants.SUCCESS_MESSAGE);
			} else {
				status = new Status(ApplicationConstants.FAILURE_MESSAGE);
			}

		} catch (NullPointerException exception) {
			throw new BusinessException(ExceptionConstants.NULL_POINTER, ExceptionConstants.LATE_PAYMENT,
					"Value must not be null");
		}
		return status;
	}

	/**
	 * calculate interest for debit transaction before payment due date.
	 * 
	 * @param accountDetails
	 */
	private void calculateDebitTransactionsBeforeDueDate(AccountDetails accountDetails) {
		try {
			transactionDetailsRepository.fetchTransactionDetails(accountDetails.getStatementDate(),
					accountDetails.getPaymentDate(), ApplicationConstants.DEBIT, accountDetails.getAccountId())
					.forEach(debitTransaction -> {
						fromDate = debitTransaction.getTransactionDate();
						calculateInterest(debitTransaction.getTransactionAmount(),
								calculateNextMonthOfGivenDate(dateFormat.format(accountDetails.getStatementDate())),
								accountDetails.getCreditCardDetails().getInterestRate());
					});
		} catch (NullPointerException exception) {
			throw new BusinessException(ExceptionConstants.NULL_POINTER, ExceptionConstants.LATE_PAYMENT,
					"Could not be assign null value");
		}
	}

	/**
	 * calculate interest for credit transaction before payment due date
	 * 
	 * @param accountDetails
	 */
	private void calculateCreditTransactionsBeforeDueDate(AccountDetails accountDetails) {
		try {
			List<TransactionDetails> transactionDetailsList = transactionDetailsRepository.fetchTransactionDetails(
					accountDetails.getStatementDate(), accountDetails.getPaymentDate(), ApplicationConstants.CREDIT,
					accountDetails.getAccountId());
			// calculate interest for full amount not paid
			if (transactionDetailsList.isEmpty()) {
				calculateInterest(ApplicationConstants.ZERO, accountDetails.getPaymentDate(),
						accountDetails.getCreditCardDetails().getInterestRate());
			}
			// calculate interest for paid some amount but not paid full amount
			else {
				transactionDetailsRepository.fetchTransactionDetailsList(accountDetails.getStartDateTime(),
						accountDetails.getStatementDate(), ApplicationConstants.DEBIT, accountDetails.getAccountId())
						.forEach(a -> {
							totalCashAmount += a.getTransactionAmount();
						});
				if (totalCashAmount == ApplicationConstants.ZERO) {
					transactionDetailsList.forEach(creditTransactionDetails -> {
						calculateInterest(creditTransactionDetails.getTransactionAmount(),
								creditTransactionDetails.getTransactionDate(),
								accountDetails.getCreditCardDetails().getInterestRate());
					});
				} else {
					cashStatus = ApplicationConstants.TRUE;
					calculateInterestWithOutCash(totalCashAmount, transactionDetailsList, accountDetails);
				}
			}
		} catch (NullPointerException exception) {
			throw new BusinessException(ExceptionConstants.NULL_POINTER, ExceptionConstants.LATE_PAYMENT,
					"Could not be assign null value");
		}
	}

	/**
	 * calculate interest for cash Debit customers without cash advance before
	 * payment due date
	 * 
	 * @param cashTransactionAmount
	 * @param transactionDetailsList
	 * @param accountDetails
	 */
	private void calculateInterestWithOutCash(Double totalCashAmount, List<TransactionDetails> transactionDetailsList,
			AccountDetails accountDetails) {
		try {
			cashAmount = totalCashAmount;
			transactionDetailsList.forEach(creditTransactionDetails -> {
				cashAmount -= creditTransactionDetails.getTransactionAmount();
				transactionDate = creditTransactionDetails.getTransactionDate();
			});
			if (cashAmount < ApplicationConstants.ZERO) {
				cashStatus = ApplicationConstants.FALSE;
				calculateInterest(-cashAmount, transactionDate,
						accountDetails.getCreditCardDetails().getInterestRate());
			} else {
				calculateInterest(ApplicationConstants.ZERO, transactionDate,
						accountDetails.getCreditCardDetails().getInterestRate());
				List<LateInterest> lateInterestDetails = lateInterestRepository
						.fetchLateInterestDetails(accountDetails.getAccountId());
				if (lateInterestDetails.isEmpty()) {
					lateInterestRepository.save(new LateInterest(accountDetails.getPaymentDate(), interestAmount,
							balanceAmount, cashAmount, accountDetails));
				} else {
					lateInterestDetails.forEach(lateInterest -> {
						lateInterestRepository.updateLateInterest(accountDetails.getPaymentDate(), interestAmount,
								balanceAmount, cashAmount, lateInterest.getLateInterestId());
					});
				}
			}

		} catch (NullPointerException exception) {
			throw new BusinessException(ExceptionConstants.NULL_POINTER, ExceptionConstants.LATE_PAYMENT,
					"Could not be assign null value");
		}
	}

	/**
	 * update late interest Details
	 * 
	 * @param accountDetails
	 */
	private void updateLateInterestDetails(AccountDetails accountDetails) {
		List<LateInterest> lateInterestDetails = lateInterestRepository
				.fetchLateInterestDetails(accountDetails.getAccountId());
		if (cashStatus == ApplicationConstants.FALSE) {
			if (lateInterestDetails.isEmpty()) {
				lateInterestRepository.save(new LateInterest(accountDetails.getPaymentDate(), interestAmount,
						balanceAmount, ApplicationConstants.ZERO, accountDetails));
			} else {
				lateInterestDetails.forEach(lateInterest -> {
					lateInterestRepository.updateLateInterest(accountDetails.getPaymentDate(), interestAmount,
							balanceAmount, ApplicationConstants.ZERO, lateInterest.getLateInterestId());
				});
			}
		}
	}

	/**
	 * Calculate interest after payment due date
	 */
	@Transactional
	public Status calculateInterestAfterPaymentDueDate() {
		try {
			List<AccountDetails> accountDetailsList = accountDetailsRepository
					.fetchAccountDetails(ApplicationConstants.PROCESSING);
			if (!accountDetailsList.isEmpty()) {
				accountDetailsList.stream().distinct().collect(Collectors.toList()).forEach(accountDetails -> {
					interestAmount = ApplicationConstants.ZERO;
					fetchLateInterest(accountDetails.getAccountId());
					availableCreditLimit = accountDetails.getAvailableCreditLimit();
					calculateCreditTransactionsAfterDueDate(accountDetails);
					calculateDebitTransactionsAfterDueDate(accountDetails);
					availableCreditLimit -= interestAmount;
					updateLateInterest(accountDetails);
					accountDetailsRepository.updateAccountDetails(availableCreditLimit, accountDetails.getAccountId());
				});
				status = new Status(ApplicationConstants.SUCCESS_MESSAGE);
			} else {
				status = new Status(ApplicationConstants.FAILURE_MESSAGE);
			}
		} catch (NullPointerException exception) {
			throw new BusinessException(ExceptionConstants.NULL_POINTER, ExceptionConstants.LATE_PAYMENT,
					"Value must not be null");
		}
		return status;
	}

	/**
	 * update late interest
	 * 
	 * @param accountDetails
	 */
	private void updateLateInterest(AccountDetails accountDetails) {
		lateInterestRepository.updateLateInterest(accountDetails.getPaymentDate(), interestAmount, balanceAmount,
				ApplicationConstants.ZERO, lateInterestId);
	}

	/**
	 * calculate interest for debit transactions after payment due date
	 * 
	 * @param accountDetails
	 */
	private void calculateDebitTransactionsAfterDueDate(AccountDetails accountDetails) {
		try {
			accountDetailsRepository
					.fetchAfterDueDateTransactionDetails(ApplicationConstants.DEBIT, accountDetails.getAccountId())
					.forEach(afterDueDateDebitDetails -> {
						fromDate = afterDueDateDebitDetails.getTransactionDate();
						calculateInterest(afterDueDateDebitDetails.getTransactionAmount(),
								accountDetails.getStatementDate(),
								accountDetails.getCreditCardDetails().getInterestRate());
					});
		} catch (NullPointerException exception) {
			throw new BusinessException(ExceptionConstants.NULL_POINTER, ExceptionConstants.LATE_PAYMENT,
					"Could not be assign null value");
		}
	}

	/**
	 * calculate interest for credit transactions after payment due date
	 * 
	 * @param accountDetails
	 */
	private void calculateCreditTransactionsAfterDueDate(AccountDetails accountDetails) {
		try {
			List<ITransactionCustomized> transactionDetailsList = accountDetailsRepository
					.fetchAfterDueDateTransactionDetails(ApplicationConstants.CREDIT, accountDetails.getAccountId());
			// transaction details is empty so calculate interest for each day
			if (transactionDetailsList.isEmpty()) {
				calculateInterest(ApplicationConstants.ZERO, accountDetails.getStatementDate(),
						accountDetails.getCreditCardDetails().getInterestRate());
			}
			// calculate interest for payment date to next transaction or last transaction
			// to next transaction
			else {
				lateInterestRepository.fetchLateInterestDetails(accountDetails.getAccountId()).forEach(lateInterest -> {
					totalCashAmount = lateInterest.getCashAmount();
				});
				if (totalCashAmount == ApplicationConstants.ZERO) {
					transactionDetailsList.forEach(afterDueDateCreditDetails -> {
						calculateInterest(afterDueDateCreditDetails.getTransactionAmount(),
								afterDueDateCreditDetails.getTransactionDate(),
								accountDetails.getCreditCardDetails().getInterestRate());
					});
				} else {
					cashStatus = ApplicationConstants.TRUE;
					calculateInterestWithOutCashAmount(totalCashAmount, transactionDetailsList, accountDetails);
				}
			}
		} catch (NullPointerException exception) {
			throw new BusinessException(ExceptionConstants.NULL_POINTER, ExceptionConstants.LATE_PAYMENT,
					"Could not be assign null value");
		}
	}

	/**
	 * calculate interest cash debit customers after payment due date any credit
	 * 
	 * @param totalCashAmount
	 * @param transactionDetailsList
	 * @param accountDetails
	 */
	private void calculateInterestWithOutCashAmount(Double totalCashAmount,
			List<ITransactionCustomized> transactionDetailsList, AccountDetails accountDetails) {
		cashAmount = totalCashAmount;
		transactionDetailsList.forEach(creditTransactionDetails -> {
			cashAmount -= creditTransactionDetails.getTransactionAmount();
			transactionDate = creditTransactionDetails.getTransactionDate();
		});
		if (cashAmount < ApplicationConstants.ZERO) {
			cashStatus = ApplicationConstants.FALSE;
			calculateInterest(-cashAmount, transactionDate, accountDetails.getCreditCardDetails().getInterestRate());
		} else {
			calculateInterest(ApplicationConstants.ZERO, transactionDate,
					accountDetails.getCreditCardDetails().getInterestRate());
			List<LateInterest> lateInterestDetails = lateInterestRepository
					.fetchLateInterestDetails(accountDetails.getAccountId());
			if (lateInterestDetails.isEmpty()) {
				lateInterestRepository.save(new LateInterest(accountDetails.getPaymentDate(), interestAmount,
						balanceAmount, cashAmount, accountDetails));
			} else {
				lateInterestDetails.forEach(lateInterest -> {
					lateInterestRepository.updateLateInterest(accountDetails.getPaymentDate(), interestAmount,
							balanceAmount, cashAmount, lateInterest.getLateInterestId());
				});
			}
		}
	}

	/**
	 * calculate total purchase amount
	 * 
	 * @param accountDetails
	 */
	private void calculatePurchaseAmount(AccountDetails accountDetails) {
		balanceAmount = transactionDetailsRepository.fetchTransactionAmount(accountDetails.getStartDateTime(),
				accountDetails.getStatementDate(), accountDetails.getAccountId());
	}

	/**
	 * fetch loan interest details and assign late interest values
	 * 
	 * @param accountDetails
	 */
	private void fetchLateInterest(Integer accountId) {
		try {
			lateInterestRepository.fetchLateInterestDetails(accountId).forEach(lateInterest -> {
				fromDate = lateInterest.getProcessingDate();
				balanceAmount = lateInterest.getBalanceAmount();
				interestAmount = lateInterest.getInterestAmount();
				lateInterestId = lateInterest.getLateInterestId();
			});
		} catch (NullPointerException exception) {
			throw new BusinessException(ExceptionConstants.NULL_POINTER, ExceptionConstants.LATE_PAYMENT,
					"Value must not be null");
		}
	}

	/**
	 * fetch Current Date
	 * 
	 * @return currentDate
	 */
	private Date fetchCurrentDate() {
		try {
			LocalDate localDate = LocalDate.now();
			Date currentProcessingDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			return currentProcessingDate;
		} catch (DateTimeParseException exception) {
			throw new BusinessException(ExceptionConstants.DATE_ERROR, ExceptionConstants.LATE_PAYMENT,
					exception.getMessage());
		}
	}

	/**
	 * calculate interest
	 * 
	 * @param transactionDetails
	 * @param accountDetails
	 * 
	 */
	private void calculateInterest(Double transactionAmount, Date transactionDate, Double interestRate) {
		try {
			long count = ChronoUnit.DAYS.between(LocalDate.parse(dateFormat.format(fromDate)),
					LocalDate.parse(dateFormat.format(transactionDate)));
			interestAmount += (balanceAmount * (interestRate / ApplicationConstants.HUNDRED)
					* ApplicationConstants.MONTH * count) / ApplicationConstants.DAYS_OF_THE_YEAR;
			fromDate = transactionDate;
			balanceAmount -= transactionAmount;
		} catch (NullPointerException exception) {
			throw new BusinessException(ExceptionConstants.NULL_POINTER, ExceptionConstants.LATE_PAYMENT,
					"Value must not be null");
		} catch (DateTimeParseException exception) {
			throw new BusinessException(ExceptionConstants.DATE_ERROR, ExceptionConstants.LATE_PAYMENT,
					exception.getMessage());
		}
	}

	/**
	 * calculate date for next month of given date
	 * 
	 * @param date
	 * @return nextDate
	 */
	private Date calculateNextMonthOfGivenDate(String date) {
		try {
			Calendar calender = Calendar.getInstance();
			calender.setTime(dateFormat.parse(date));
			calender.add(Calendar.MONTH, 1);
			nextDate = dateFormat.parse(dateFormat.format(calender.getTime()));
		} catch (ParseException exception) {
			throw new BusinessException(ExceptionConstants.DATE_ERROR, ExceptionConstants.LATE_PAYMENT,
					exception.getMessage());
		}
		return nextDate;
	}
}