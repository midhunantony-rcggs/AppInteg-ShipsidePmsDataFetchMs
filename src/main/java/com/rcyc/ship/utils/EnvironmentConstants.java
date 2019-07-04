/**
 * 
 */
package com.rcyc.ship.utils;

/**
 * @author Muhammed Aboobacker Created :28-Nov-2018
 */
public class EnvironmentConstants {

	// AES encryption key

	public static final String ENCRYPTION_KEY = System.getenv("RCYC_KEY_VALUE");

	// Database environment constants

	public static final String DB_URL = CryptoUtil.decrypt(System.getenv("DB_URL"));

	public static final String DB_USERNAME = CryptoUtil.decrypt(System.getenv("DB_USERNAME"));

	public static final String DB_PASSWORD = CryptoUtil.decrypt(System.getenv("DB_PASSWORD"));

	// Mail environment constants

	

}
