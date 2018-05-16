package testcase;

import dataBase.UsersDataBase;
import actors.Client;
import actors.Cnp;
import actors.InvalidCnpException;
import actors.User;
import actors.User.Role;
import junit.framework.Assert;
import junit.framework.TestCase;

public class Tests extends TestCase {
	private Cnp	cnp;
	
	public Tests(String s) {
		super(s);
	}

	protected void setUp() {
		cnp = new Cnp();
	}
	
	protected void tearDown() {
		cnp = null;
	}
	
	public void	testValidCnp() {
		try {
			cnp.setCnp("2921102389487");
		} catch (InvalidCnpException e) {
			// TODO Auto-generated catch block
			Assert.fail();;
		}
	}	
	
	public void	testInvalidNumbersCnp() {
		try {
			cnp.setCnp("2013003001462");
			Assert.fail();
		} catch (InvalidCnpException e) {
			// TODO Auto-generated catch block
		}
	}	
	
	public void	testInvalidLengthCnp() {
		try {
			cnp.setCnp("292110238948");
			Assert.fail();
		} catch (InvalidCnpException e) {
			// TODO Auto-generated catch block
		}
	}
	
	public void	testInvalidCharsCnp() {
		try {
			cnp.setCnp("2921a0238b4 7");
			Assert.fail();
		} catch (InvalidCnpException e) {
			// TODO Auto-generated catch block
		}
	}
	
	public void	testValidName() {
		boolean	valid;
		
		valid = Client.validateName("Alexandru");
		Assert.assertTrue(valid); 
	}
	
	public void	testInvalidName() {
		boolean	valid;
		
		valid = Client.validateName("Alexandru2");
		Assert.assertFalse(valid); 
	}
	
	public void	testValidEmail() {
		boolean	valid;
		
		valid = Client.validateEmail("viorel.nedeloiu@gmail.com");
		Assert.assertTrue(valid); 
	}	
	
	public void	test1InvalidEmail() {
		boolean	valid;
		
		valid = Client.validateEmail("viorel.nedeloiu@com");
		Assert.assertFalse(valid); 
	}	
	
	public void	test2InvalidEmail() {
		boolean	valid;
		
		valid = Client.validateEmail("email invalid");
		Assert.assertFalse(valid); 
	}		
	
	public void	test1ValidPhoneNumber() {
		boolean	valid;
		
		valid = Client.validatePhone("+37368143544");
		Assert.assertTrue(valid); 
	}	
	
	public void	test2ValidPhoneNumber() {
		boolean	valid;
		
		valid = Client.validatePhone("0748 638 288");
		Assert.assertTrue(valid); 
	}
	
	public void	test1InvalidPhoneNumber() {
		boolean	valid;
		
		valid = Client.validatePhone("00748 638288");
		Assert.assertFalse(valid); 
	}
	
	public void	test2InvalidPhoneNumber() {
		boolean	valid;
		
		valid = Client.validatePhone("0748 63a8288");
		Assert.assertFalse(valid); 
	}
	
	public void	test3InvalidPhoneNumber() {
		boolean	valid;
		
		valid = Client.validatePhone("123");
		Assert.assertFalse(valid); 
	}

	public void	test1LogareClient() {
		User	user;
		
		user = UsersDataBase.getUser("client", "client");
		Assert.assertEquals(Role.client, user.getRole());
	}
	
	public void	test2LogareClient() {
		User	user;
		
		user = UsersDataBase.getUser("client", "parola incorecta");
		Assert.assertNull(user);
	}
	
	public void	test1LogareFunctionar() {
		User	user;
		
		user = UsersDataBase.getUser("functionar", "functionar");
		Assert.assertEquals(Role.functionar, user.getRole());
	}
	
	public void	test2LogareFunctionar() {
		User	user;
		
		user = UsersDataBase.getUser("functionar", "parola incorecta");
		Assert.assertNull(user);
	}
	
	public void	test1LogareSofer() {
		User	user;
		
		user = UsersDataBase.getUser("sofer", "sofer");
		Assert.assertEquals(Role.sofer, user.getRole());
	}
	
	public void	test2LogareSofer() {
		User	user;
		
		user = UsersDataBase.getUser("sofer", "parola incorecta");
		Assert.assertNull(user);
	}
	
}
