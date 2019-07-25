package com.rcyc.ship.service;

import com.rcyc.ship.dto.PMSDataRequest;

public interface PmsDataFetchService {

	void fetchDataFromPmsOld(PMSDataRequest data) throws Exception;

	void fetchDataFromPms(PMSDataRequest request) throws Exception;

}
