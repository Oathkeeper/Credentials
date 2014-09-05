package ca.owenpeterson.service;


import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class CredentialService {
	
	static Logger logger = Logger.getLogger(CredentialService.class);
	
	private static final char[] RESTRICTED_CHARS = " !#$%&'*+-/=?^_`{|}~@".toCharArray();
	private static final char[] RESTRICTED_FIRST_CHARS = "_-@".toCharArray(); 
	private static final char[] PASSWORD_CHARS = "!#$%&'()*+,-./23456789:;<=>?@ABCDEFGHJKLMNOPRSTUVWXYZ[\\]^_abcdefghijkmnopqrstuvwxyz{|}~".toCharArray();
	private static final int PASSWORD_LENGTH = 12;
	
	public CredentialService() {
	}
	
	public String generateUsername(String emailAddress) {
		logger.debug("Starting to generate username for: " + emailAddress);
		
		if (StringUtils.isBlank(emailAddress)) {
			throw new IllegalArgumentException("emailAddress cannot be blank!");
		}
		String username = emailAddress;
		
		while(hasBadFirstChar(username)) {
			logger.debug("Bad first character found!");
			username = stripFirstChar(username);
			logger.debug("Username with stripped firstchar: " + username);		
		}
		
		username = stripDomain(username);
		
		if (hasBadOtherChars(username)) {
			for (char c:RESTRICTED_CHARS) {
				if (StringUtils.contains(username, c)) {
					username = StringUtils.remove(username, c);
				}
			}
		}
		
		logger.debug("Finished generating username. New username is: " + username);
		return username;
	}
	
	
	public String generatePassword() {
		logger.debug("Generating a new random password.");
		StringBuilder passwordBuilder = new StringBuilder();
		String password;
		int[] randomNumbers = getRandomNumberArray(PASSWORD_LENGTH);
		
		for (int rand : randomNumbers) {
			passwordBuilder.append(PASSWORD_CHARS[rand]);
		}
		
		password = passwordBuilder.toString();
		
		logger.debug("returning new random password.");
		return password;
	}
	
	private boolean hasBadFirstChar(String emailAddress) {
		logger.debug("Checking for a bad first character in address: " + emailAddress);
		boolean hasBad = false;
		
		for (char c:RESTRICTED_FIRST_CHARS) {
			if (StringUtils.startsWith(emailAddress, String.valueOf(c))) {
				hasBad = true;
			}
		}
		
		logger.debug("returning result of: " + hasBad);
		return hasBad;
		
	}
	
	private boolean hasBadOtherChars(String emailAddress) {
		logger.debug("Checking for bad special characters in address: " + emailAddress);
		boolean hasBad = false;
		
		for (char c:RESTRICTED_CHARS) {
			if (StringUtils.contains(emailAddress, c)) {
				hasBad = true;
			}
		}
		
		logger.debug("returning result of: " + hasBad);
		return hasBad;	
		
	}
	
	private String stripFirstChar(String emailAddress) {
		logger.debug("Stripping first character from email address: " + emailAddress);
		String username = emailAddress;
		
		if (StringUtils.isNotEmpty(username)) {
			for (int i = 0; ( (i < (RESTRICTED_FIRST_CHARS.length))); i++) {
				if (StringUtils.startsWith(username, String.valueOf(RESTRICTED_FIRST_CHARS[i]))) {
					logger.debug("Match Found for character: " + RESTRICTED_FIRST_CHARS[i]);
					username = StringUtils.removeStart(username, String.valueOf(RESTRICTED_FIRST_CHARS[i]));
					return username;
				}
			}
		}		
		
		logger.debug("returning username with bad first chars removed: " + username);
		return username;
	}
	
	
	private String stripDomain(String emailAddress) {
		logger.debug("Stripping domain from email address: " + emailAddress);
		String noDomainUsername = emailAddress;		
		noDomainUsername = StringUtils.substringBeforeLast(emailAddress, "@");
		
		logger.debug("returning email without the domain: " + noDomainUsername);
		return noDomainUsername;
	}
	
	private int getRandomNumberRange(int min, int max) {
		logger.debug("CredentialService(): getRandomNumberRange(min= " + min + " max= " + max + "): Begin");
		Random random = new Random();
		int randomNum;
		
		randomNum = random.nextInt((max - min) + 1) + min;
		
		logger.debug("CredentialService(): getRandomNumberRange(): returning= " + randomNum + ": End");
		return randomNum;
	}
	
	private int[] getRandomNumberArray(int size) {
		logger.debug("CredentialService(): getRandomNumberArray(size= " + size + "): Begin");
		int[] randomNumbers = new int[size];
		int tempNumber;
		
		for (int i = 0; i < size; i ++) {
			tempNumber = getRandomNumberRange(0, PASSWORD_CHARS.length);
			randomNumbers[i] = tempNumber;
		}
		
		logger.debug("CredentialService(): getRandomNumberArray(): End");
		return randomNumbers;
	}

}
