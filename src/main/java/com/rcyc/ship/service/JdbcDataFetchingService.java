package com.rcyc.ship.service;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.rcyc.ship.utils.SqlQueries;
import com.rcyc.ship.utils.Constants;

@Service
public class JdbcDataFetchingService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	private final Logger log = LoggerFactory.getLogger(JdbcDataFetchingService.class);

	public boolean insertPmsDataToTable(Object data) {
		try {
			log.info("Start insertPmsDataToTable::");
			SimpleDateFormat normalDateFormat = new SimpleDateFormat(Constants.DATE_TIME_PATTERN_FROM_DB);
			int status = jdbcTemplate.update(SqlQueries.insertPmsDataToTable, data,
					normalDateFormat.format(new Date()));
			log.info("End insertPmsDataToTable::");
			return status == 1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Error insertPmsDataToTable::");
			return false;
		}
	}
}
