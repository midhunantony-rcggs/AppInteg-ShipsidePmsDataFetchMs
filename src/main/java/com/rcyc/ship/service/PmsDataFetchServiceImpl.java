package com.rcyc.ship.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.rcyc.ship.controller.PmsDataFetchController;
import com.rcyc.ship.dto.AuditLog;
import com.rcyc.ship.dto.PMSDataRequest;
import com.rcyc.ship.exception.GeneralException;
import com.rcyc.ship.utils.Constants;
import com.rcyc.ship.utils.ExceptionMessages;
import com.rcyc.ship.utils.VMWareConnection;

@Service
public class PmsDataFetchServiceImpl implements PmsDataFetchService {

	@Autowired
	JdbcDataFetchingService jdbcDataFetchingService;

	private final Logger log = LoggerFactory.getLogger(PmsDataFetchService.class);

//	@Autowired
//	private KafkaTemplate<Object, Object> template;
	
	@Autowired
	VMWareConnection vmWareConnection;

	@Override
	public void fetchDataFromPms(PMSDataRequest request) throws Exception {

		log.info("Start Service fetchDateFromPms ::");

//		AuditLog auditLog = new AuditLog("PMS Data Fetch", "folioTx", "bookingTx", 0,
//				new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(new Date()), Constants.AUDIT_LOG_INFO_TYPE,
//				"Data Transfered to PMSDataDB.", "Service", request.getDataType());
//		this.template.send(Constants.AUDIT_LOG_TOPIC, auditLog);

		boolean status = jdbcDataFetchingService.insertPmsDataToTable(request.getData());

		log.info("End Service fetchDateFromPms ::");
		if (!status) {
			log.info("Service fetchDateFromPms Error::");

//			auditLog = new AuditLog("PMS Data Fetch", "folioTx", "bookingTx", 0,
//					new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(new Date()), Constants.AUDIT_LOG_ERROR_TYPE,
//					"Data Transfered to PMSDataDB - Failure.", "Service", request.getDataType());
//			this.template.send(Constants.AUDIT_LOG_TOPIC, auditLog);

			throw new GeneralException(ExceptionMessages.PMS_DATA_INSERT_FAILURE, 478);
		} else
		{

//			auditLog = new AuditLog("PMS Data Fetch", "folioTx", "bookingTx", 0,
//					new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(new Date()), Constants.AUDIT_LOG_INFO_TYPE,
//					"Data Transfered to Kafka .", "Service", request.getDataType());
//		this.template.send(Constants.AUDIT_LOG_TOPIC, auditLog);
		
//		this.template.send(Constants.PMS_DATA_FETCH_TOPIC, request);
			
			vmWareConnection.sendRequestToVmWare("api/sendRequestToVmWare",request.getData());
		}
	}

}
