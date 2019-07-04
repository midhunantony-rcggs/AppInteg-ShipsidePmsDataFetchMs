package com.rcyc.ship.service;

import com.rcyc.ship.dto.PMSDataRequest;

public interface PmsDataFetchService {

	void fetchDataFromPms(PMSDataRequest data) throws Exception;
	
}
