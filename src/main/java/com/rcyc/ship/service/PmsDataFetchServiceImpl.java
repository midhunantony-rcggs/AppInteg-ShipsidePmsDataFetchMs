package com.rcyc.ship.service;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.rcyc.ship.controller.PmsDataFetchController;
import com.rcyc.ship.dto.AuditLog;
import com.rcyc.ship.dto.PMSDataRequest;
import com.rcyc.ship.dto.PmsDataDto;
import com.rcyc.ship.dto.PmsDataListDTO;
import com.rcyc.ship.exception.GeneralException;
import com.rcyc.ship.model.PmsDataModel;
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

//	@Override
//	public void fetchDataFromPms(PMSDataRequest request) throws Exception {
//		System.out.println("request.getData()::");
//
//		log.info("Start Service fetchDateFromPms ::");
//
////		AuditLog auditLog = new AuditLog("PMS Data Fetch", "folioTx", "bookingTx", 0,
////				new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(new Date()), Constants.AUDIT_LOG_INFO_TYPE,
////				"Data Transfered to PMSDataDB.", "Service", request.getDataType());
////		this.template.send(Constants.AUDIT_LOG_TOPIC, auditLog);
//
//		boolean status = jdbcDataFetchingService.insertPmsDataToTable(request.getData());
//
//		log.info("End Service fetchDateFromPms ::");
//		if (!status) {
//			log.info("Service fetchDateFromPms Error::");
//
////			auditLog = new AuditLog("PMS Data Fetch", "folioTx", "bookingTx", 0,
////					new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(new Date()), Constants.AUDIT_LOG_ERROR_TYPE,
////					"Data Transfered to PMSDataDB - Failure.", "Service", request.getDataType());
////			this.template.send(Constants.AUDIT_LOG_TOPIC, auditLog);
//
//			throw new GeneralException(ExceptionMessages.PMS_DATA_INSERT_FAILURE, 478);
//		} else {
//
////			auditLog = new AuditLog("PMS Data Fetch", "folioTx", "bookingTx", 0,
////					new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(new Date()), Constants.AUDIT_LOG_INFO_TYPE,
////					"Data Transfered to Kafka .", "Service", request.getDataType());
////		this.template.send(Constants.AUDIT_LOG_TOPIC, auditLog);
//
////		this.template.send(Constants.PMS_DATA_FETCH_TOPIC, request);
//			log.info(request.getData());
//			InputSource is = new InputSource();
//			is.setCharacterStream(new StringReader(request.getData()));
//
//			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//			DocumentBuilder db = dbf.newDocumentBuilder();
//			Document doc = db.parse(is);
//			NodeList nodes = doc.getElementsByTagName("EndDatabaseSwap");
//			if (nodes != null && nodes.getLength() > 0) {
//				log.info("IFF");
//				List<PmsDataModel> startDateSwapList = jdbcDataFetchingService.findLatestStartSwapID();
//				PmsDataModel startDateSwapObj = new PmsDataModel();
//				log.info("SIZE::"+startDateSwapList.size());
//				if (startDateSwapList != null && startDateSwapList.size() > 0) {
//					startDateSwapObj = startDateSwapList.get(0);
//				}
//				if (startDateSwapObj.getId()>0) {
//					log.info("IFF2");
//					List<PmsDataModel> bulkDataList = jdbcDataFetchingService
//							.findLatestBulkDatas(startDateSwapObj.getId());
//					System.out.println(bulkDataList.size());
//					log.info(bulkDataList.toString());
//					PmsDataListDTO pmsDataListDTOObj=new PmsDataListDTO();
//					List<PmsDataDto> pmsDataDtoList=new ArrayList<>();
//					for(PmsDataModel pmsDataModelObj:bulkDataList) {
//						PmsDataDto pmsDataDtoObj=new PmsDataDto();
//						pmsDataDtoObj.setData(pmsDataModelObj.getData());
//						pmsDataDtoObj.setId(pmsDataModelObj.getId());
//						pmsDataDtoObj.setUpdateddate(pmsDataModelObj.getUpdateddate());
//						pmsDataDtoList.add(pmsDataDtoObj);
//					}
//					pmsDataListDTOObj.setPmsDataDtoList(pmsDataDtoList);
//					vmWareConnection.sendRequestToVmWare("api/sendRequestToVmWare", pmsDataListDTOObj);
//				}
//
//			}
//
//		}
//	}
	
	@Override
	public void fetchDataFromPms(PMSDataRequest request) throws Exception {
		System.out.println("request.getData()::");

		log.info("Start Service fetchDateFromPms ::");
		boolean status = jdbcDataFetchingService.insertPmsDataToTable(request.getData());

		log.info("End Service fetchDateFromPms ::");
		if (!status) {
			log.info("Service fetchDateFromPms Error::");

			throw new GeneralException(ExceptionMessages.PMS_DATA_INSERT_FAILURE, 478);
		} else {
			log.info(request.getData());
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(request.getData()));

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("EndDatabaseSwap");
			if (nodes != null && nodes.getLength() > 0) {
				List<PmsDataModel> startDateSwapList = jdbcDataFetchingService.findLatestStartSwapID();
				PmsDataModel startDateSwapObj = new PmsDataModel();
				if (startDateSwapList != null && startDateSwapList.size() > 0) {
					startDateSwapObj = startDateSwapList.get(0);
				}
				if (startDateSwapObj.getId()>0) {
					log.info("IFF");
					String messageIdentifier = UUID.randomUUID().toString()
							+ new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
					List<PmsDataModel> bulkDataList = jdbcDataFetchingService
							.findLatestBulkDatas(startDateSwapObj.getId());
					log.info(Integer.toString(startDateSwapObj.getId()));
					for(PmsDataModel pmsDataModelObj:bulkDataList) {
						log.info(pmsDataModelObj.getData());
						vmWareConnection.sendRequestToVmWare("api/sendRequestToVmWare", pmsDataModelObj.getData(),messageIdentifier);
					}
				}

			}

		}
	}

}
