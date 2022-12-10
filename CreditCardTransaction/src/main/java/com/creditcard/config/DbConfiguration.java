package com.creditcard.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DbConfiguration {

	@Bean(name = "dbConfig")
	@ConfigurationProperties(prefix = "spring.datasource")
	@Primary
	public DataSource createDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "jdbcService")
	@Autowired
	public JdbcTemplate createJdbcTemplate_UserService(@Qualifier("dbConfig") DataSource datasourceConfig) {
		return new JdbcTemplate(datasourceConfig);
	}
//
//	@Bean
//	public LockProvider lockProvider(DataSource dataSource) {
//		return new JdbcTemplateLockProvider(dataSource);
//	}

}
