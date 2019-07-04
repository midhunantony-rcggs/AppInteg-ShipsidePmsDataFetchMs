package com.rcyc.ship.configurations;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.rcyc.ship.utils.EnvironmentConstants;


/**
 * 
 * Database Configurations
 *
 */
@Configuration
public class DatabaseConfiguration {
	
	@Value("${db.driver}")
	private String databaseDriver;
	
	
	
  
	private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(databaseDriver);
//		dataSource.setUrl(EnvironmentConstants.DB_URL);
//		dataSource.setUsername(EnvironmentConstants.DB_USERNAME);
//		dataSource.setPassword(EnvironmentConstants.DB_PASSWORD);
		
		dataSource.setUrl("jdbc:mysql://localhost:3306/PmsData");
	       dataSource.setUsername("root");
	       dataSource.setPassword("root");
		return dataSource;
	}
	

}
