
package com.rcyc.ship.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rcyc.ship.dto.AuditLog;
import com.rcyc.ship.dto.PMSDataRequest;
import com.rcyc.ship.dto.StatusResponse;
import com.rcyc.ship.service.JdbcDataFetchingService;
import com.rcyc.ship.service.PmsDataFetchService;
import com.rcyc.ship.utils.Constants;

@RestController
@RequestMapping("/api")
public class PmsDataFetchController {

	@Autowired
	PmsDataFetchService pmsDataFetchService;

//	@Autowired
//	private KafkaTemplate<Object, Object> template;

	private final Logger log = LoggerFactory.getLogger(PmsDataFetchController.class);

	@PostMapping(value = "/sendData", consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_PLAIN_VALUE,
			MediaType.TEXT_XML_VALUE })
	public StatusResponse getGuestDetail(@RequestBody String data) throws Exception {
		log.info("Start controller getGuestDetail::");
		if (data != null && !data.equals("")) {
			String messageIdentifier = UUID.randomUUID().toString()
					+ new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			
//			AuditLog auditLog = new AuditLog("PMS Data Fetch", "folioTx", "bookingTx", 0,
//					new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(new Date()), "INFO",
//					"Data recieved from IBS.", "Controller", messageIdentifier);
//			this.template.send(Constants.AUDIT_LOG_TOPIC, auditLog);
			
			PMSDataRequest request = new PMSDataRequest(data, messageIdentifier);
			pmsDataFetchService.fetchDataFromPms(request);
		}
		log.info("END controller getGuestDetail::");
		return new StatusResponse("A", "Success",HttpStatus.SC_OK,null);

	}

}
