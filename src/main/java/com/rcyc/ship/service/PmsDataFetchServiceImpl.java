package com.rcyc.ship.service;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
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
	
	@Override
	public void fetchDataFromPmsOld(PMSDataRequest request) throws Exception {
		System.out.println("request.getData()::");

		log.info("Start Service fetchDateFromPms ::");
		boolean status = jdbcDataFetchingService.insertPmsDataToTable(request.getData());
		log.info("status ::"+status);
		
		log.info("End Service fetchDateFromPms ::");
		if (!status) {
			log.info("Service fetchDateFromPms Error::");

			throw new GeneralException(ExceptionMessages.PMS_DATA_INSERT_FAILURE, 478);
		} else {
			 vmWareConnection.sendRequestToVmWare("api/sendRequestToVmWareOld", request.getData());

		}
	}

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

				// An Async task always executes in new thread
				new Thread(new Runnable() {
					public void run() {
						List<PmsDataModel> startDateSwapList = jdbcDataFetchingService.findLatestStartSwapID();
						PmsDataModel startDateSwapObj = new PmsDataModel();
						if (startDateSwapList != null && startDateSwapList.size() > 0) {
							startDateSwapObj = startDateSwapList.get(0);
						}
						if (startDateSwapObj.getId() > 0) {
							log.info("IFF");
							String messageIdentifier = UUID.randomUUID().toString()
									+ new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
							List<PmsDataModel> bulkDataList = jdbcDataFetchingService
									.findLatestBulkDatas(startDateSwapObj.getId());
							log.info(Integer.toString(startDateSwapObj.getId()));
							for (PmsDataModel pmsDataModelObj : bulkDataList) {
								boolean status  = jdbcDataFetchingService.updatePmsDataWithMsgId(messageIdentifier,
										pmsDataModelObj.getId());
								log.info(pmsDataModelObj.getData());
								if (status) {
									try {
										vmWareConnection.sendRequestToVmWare("api/sendRequestToVmWare",
												pmsDataModelObj.getData(), messageIdentifier);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} else {
									throw new GeneralException(ExceptionMessages.PMS_DATA_MSG_ID_UPDATEFAILURE, 478);
								}

							}
						}
					}
				}).start();

			}

		}
	}

}
