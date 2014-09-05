package service;

import org.junit.Test;

import ca.owenpeterson.service.CredentialService;
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
	
	@Test
	public void testBadFirstCharAt() {
		String email = "@oathkeeper@email.com";
		String actual;
		String expected;
		
		expected = "oathkeeper";
		actual = this.credentialService.generateUsername(email);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBadFirstCharMinus() {
		String email = "-oathkeeper@email.com";
		String actual;
		String expected;
		
		expected = "oathkeeper";
		actual = this.credentialService.generateUsername(email);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBadFirstCharUnderscore() {
		String email = "_oathkeeper@email.com";
		String actual;
		String expected;
		
		expected = "oathkeeper";
		actual = this.credentialService.generateUsername(email);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBadFirstCharMultipleOtherChars() {
		String email = "_oat%hk#eeper@email.com";
		String actual;
		String expected;
		
		expected = "oathkeeper";
		actual = this.credentialService.generateUsername(email);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testMultipleBadFirstChar() {
		String email = "_@oathkeeper@email.com";
		String actual;
		String expected;
		
		expected = "oathkeeper";
		actual = this.credentialService.generateUsername(email);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testMultipleBadFirstCharMultipleOtherChars() {
		String email = "_@o@at*hk#eeper@email.com";
		String actual;
		String expected;
		
		expected = "oathkeeper";
		actual = this.credentialService.generateUsername(email);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGenerateRandomPassword() {
		String password;
		
		password = this.credentialService.generatePassword();
		
		assertNotNull(password);
	}
	
	@Test
	public void testGenerateRandomPassword_CheckLength() {
		String password;
		
		password = this.credentialService.generatePassword();
		
		assertTrue("Generated password is not the correct length.", password.length() == 12);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGenerateUsername_EmptyEmail() throws Exception {
		String emailAddress = "";
		this.credentialService.generateUsername(emailAddress);
	}
	
}
