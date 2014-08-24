package ca.owenpeterson.service;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class CredentialService {
	
	static Logger logger = Logger.getLogger(CredentialService.class);
	
	private static final char[] RESTRICTED_CHARS = " !#$%&'*+-/=?^_`{|}~@".toCharArray();
	private static final char[] RESTRICTED_FIRST_CHARS = "_-@".toCharArray(); 
	
	public CredentialService() {
	}
	
	public String stripDomain(String emailAddress) {
		String noDomainUsername = emailAddress;		
		noDomainUsername = StringUtils.substringBeforeLast(emailAddress, "@");
		
		return noDomainUsername;
	}
	
	public String generateUsername(String emailaddress) {
		String username = emailaddress;
		
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
		
		return username;
	}
	
	public boolean hasBadFirstChar(String emailaddress) {
		boolean hasBad = false;
		
		for (char c:RESTRICTED_FIRST_CHARS) {
			if (StringUtils.startsWith(emailaddress, String.valueOf(c))) {
				hasBad = true;
			}
		}
		
		return hasBad;
		
	}
	
	public boolean hasBadOtherChars(String emailaddress) {
		boolean hasBad = false;
		
		for (char c:RESTRICTED_CHARS) {
			if (StringUtils.contains(emailaddress, c)) {
				hasBad = true;
			}
		}
		
		return hasBad;	
		
	}
	
	public String stripFirstChar(String emailaddress) {
		String username = emailaddress;
		
		if (StringUtils.isNotEmpty(username)) {
			for (int i = 0; ( (i < (RESTRICTED_FIRST_CHARS.length))); i++) {
				if (StringUtils.startsWith(username, String.valueOf(RESTRICTED_FIRST_CHARS[i]))) {
					logger.debug("Match Found for character: " + RESTRICTED_FIRST_CHARS[i]);
					username = StringUtils.removeStart(username, String.valueOf(RESTRICTED_FIRST_CHARS[i]));
					return username;
				}
			}
		}		
		
		return username;
	}

}
