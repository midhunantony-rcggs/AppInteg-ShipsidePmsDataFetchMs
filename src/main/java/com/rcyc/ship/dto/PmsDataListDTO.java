package com.rcyc.ship.dto;

import java.util.List;


public class PmsDataListDTO implements java.io.Serializable{
	
	private List<PmsDataDto> pmsDataDtoList;

	public List<PmsDataDto> getPmsDataDtoList() {
		return pmsDataDtoList;
	}

	public void setPmsDataDtoList(List<PmsDataDto> pmsDataDtoList) {
		this.pmsDataDtoList = pmsDataDtoList;
	}

}
