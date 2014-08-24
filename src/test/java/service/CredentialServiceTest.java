package service;

import ca.owenpeterson.service.CredentialService;
import junit.framework.Assert;
import junit.framework.TestCase;

public class CredentialServiceTest extends TestCase {
	
	CredentialService credentialService = new CredentialService();
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testBadFirstCharAt() {
		String email = "@oathkeeper@email.com";
		String actual;
		String expected;
		
		expected = "oathkeeper";
		actual = this.credentialService.generateUsername(email);
		Assert.assertEquals(expected, actual);
	}
	
	public void testBadFirstCharMinus() {
		String email = "-oathkeeper@email.com";
		String actual;
		String expected;
		
		expected = "oathkeeper";
		actual = this.credentialService.generateUsername(email);
		Assert.assertEquals(expected, actual);
	}
	
	public void testBadFirstCharUnderscore() {
		String email = "_oathkeeper@email.com";
		String actual;
		String expected;
		
		expected = "oathkeeper";
		actual = this.credentialService.generateUsername(email);
		Assert.assertEquals(expected, actual);
	}
	
	public void testBadFirstCharMultipleOtherChars() {
		String email = "_oat%hk#eeper@email.com";
		String actual;
		String expected;
		
		expected = "oathkeeper";
		actual = this.credentialService.generateUsername(email);
		Assert.assertEquals(expected, actual);
	}
	
	public void testMultipleBadFirstChar() {
		String email = "_@oathkeeper@email.com";
		String actual;
		String expected;
		
		expected = "oathkeeper";
		actual = this.credentialService.generateUsername(email);
		Assert.assertEquals(expected, actual);
	}
	
	public void testMultipleBadFirstCharMultipleOtherChars() {
		String email = "_@o@at*hk#eeper@email.com";
		String actual;
		String expected;
		
		expected = "oathkeeper";
		actual = this.credentialService.generateUsername(email);
		Assert.assertEquals(expected, actual);
	}
	
	

}
