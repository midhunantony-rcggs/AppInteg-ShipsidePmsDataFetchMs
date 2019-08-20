/**
 * 
 */
package com.rcyc.ship.utils;

/**
 * @author Midhun Antony
 * Created :20-06-2019
 */
public class SqlQueries {
	
	public static final String insertPmsDataToTable="insert into pmsdata(id,data,MsgIdentifier,updateddate) values(NULL,?,NULL,?)";
	
	public static final String insertPmsDataToTableWithMsgId="insert into pmsdata(id,data,MsgIdentifier,updateddate) values(NULL,?,?,?)";
	
	public static final String findLatestStartSwapID ="select * from pmsdata where data like '%StartDatabaseSwap%' order by id desc limit 1";
	
	public static final String findLatestBulkDatas ="select * from pmsdata where id>=?";
	
	public static final String updatePmsDataWithMsgId ="update  pmsdata set  MsgIdentifier=? where id=?";
	

}
