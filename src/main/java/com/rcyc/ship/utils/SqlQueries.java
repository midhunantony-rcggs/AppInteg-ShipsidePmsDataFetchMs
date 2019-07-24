/**
 * 
 */
package com.rcyc.ship.utils;

/**
 * @author Midhun Antony
 * Created :20-06-2019
 */
public class SqlQueries {
	
	public static final String insertPmsDataToTable="insert into pmsdata(id,data,updateddate) values(NULL,?,?)";
	
	public static final String findLatestStartSwapID ="select * from pmsdata where data like '%StartDatabaseSwap%' order by id desc limit 1";
	
	public static final String findLatestBulkDatas ="select * from pmsdata where id>=?";
	

}
