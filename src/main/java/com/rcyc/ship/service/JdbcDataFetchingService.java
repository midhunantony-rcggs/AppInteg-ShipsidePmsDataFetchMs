package com.rcyc.ship.service;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.rcyc.ship.utils.SqlQueries;
import com.rcyc.ship.model.PmsDataModel;
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
	
	public List<PmsDataModel> findLatestStartSwapID() {
		List<PmsDataModel> diningDetailsModelList=new ArrayList<>();
		try {
			diningDetailsModelList  = jdbcTemplate.query(SqlQueries.findLatestStartSwapID, 
							new BeanPropertyRowMapper<PmsDataModel>(PmsDataModel.class));
		} catch (Exception e) {
			throw e;	
			}
		return diningDetailsModelList;
	}
	
	public List<PmsDataModel> findLatestBulkDatas(int id) {
		List<PmsDataModel> diningDetailsModelList=new ArrayList<>();
		try {
			diningDetailsModelList  = jdbcTemplate.query(SqlQueries.findLatestBulkDatas, 
							new BeanPropertyRowMapper<PmsDataModel>(PmsDataModel.class),id);
		} catch (Exception e) {
			throw e;	
			}
		return diningDetailsModelList;
	}
	
	public boolean updatePmsDataWithMsgId(String msgId,Integer id) {
		try {
			log.info("Start updatePmsDataWithMsgId::");
			SimpleDateFormat normalDateFormat = new SimpleDateFormat(Constants.DATE_TIME_PATTERN_FROM_DB);
			int status = jdbcTemplate.update(SqlQueries.updatePmsDataWithMsgId, msgId,id);
			log.info("End updatePmsDataWithMsgId::");
			return status == 1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Error updatePmsDataWithMsgId::");
			return false;
		}
	}
	
}
