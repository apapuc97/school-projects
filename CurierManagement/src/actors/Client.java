package actors;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import dataBase.DataBase;
import frames.EmployeeSendForm;

public class Client {
	private int			id;
	private String		name;
	private String		surname;
	private String		cnp;
	private String		email;
	private String		phoneNumber;

	public Client() {
		this.id = 0;
		this.name = "no name";
		this.surname = "no name";
		this.cnp = "no name";
		this.email = "no name";
		this.phoneNumber = "no name";
	}
	
	public static boolean		validateName(String name) {
		if (name == null)
			return false;
		if (name.length() <= 0 || name.length() > 20)
			return false;
		if (name.length() == 0)
			return false;
		if (name.matches(".*\\d+.*"))
			return false;
		return true;
	}	
	
	public static boolean		validatePhone(String phone) {
		String	phone2;
		
		if (phone == null)
			return false;
		if (phone.length() <= 0 || phone.length() > 12)
			return false;
		if (phone.charAt(0) == '+') {
			phone = phone.replaceFirst("\\+", "");
		}
		phone = phone.replaceAll(" ", "");
		if (phone.length() <= 5)
			return false;
		try {
			phone2 = Long.parseLong(phone) + "";
			if (phone.length() - phone2.length() > 1)
				return false;
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public static boolean		validateEmail(String email) {
		String[]	elements;

		if (email == null)
			return false;
		if (email.length() <= 0 || email.length() > 30)
			return false;
		elements = email.split("@");
		if (elements.length != 2)
			return false;
		if (elements[0].length() < 2)
			return false;
		elements = elements[1].split("\\.");
		if (elements.length != 2)
			return false;
		if (elements[0].length() < 2 || elements[1].length() < 2)
			return false;
		return true;
	}	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		if (id > 0)
			this.id = id;
	}
	public String getName() {
		return name;
	}
	public boolean setName(String name) {
		if (validateName(name)) {
			this.name = name;
			return true;
		}
		else
			return false;
	}
	public void		setName(JTextField tfdName) {
		if (setName(tfdName.getText()) == false) {
			EmployeeSendForm.setErrorBorder(tfdName);
		}
		else 
			EmployeeSendForm.setDefaultBorder(tfdName);
	}
	
	public String getSurname() {
		return surname;
	}
	public boolean setSurname(String surname) {
		if (validateName(surname)) {
			this.surname = surname;
			return true;
		}
		else
			return false;
	}

	public void		setSurname(JTextField textField) {
		if (setSurname(textField.getText()) == false) {
			EmployeeSendForm.setErrorBorder(textField);
		}
		else 
			EmployeeSendForm.setDefaultBorder(textField);
	}
	public String getCnp() {
		return cnp;
	}
	public boolean setCnp(String cnp) {
		try {
			new Cnp(cnp);
			this.cnp = cnp;
		} catch (InvalidCnpException e) {
			return false;
		}
		return true;
	}	
	public void		setCnp(JTextField textField) {
		if (setCnp(textField.getText()) == false) {
			EmployeeSendForm.setErrorBorder(textField);
		}
		else 
			EmployeeSendForm.setDefaultBorder(textField);
	}
	public String getEmail() {
		return email;
	}
	public boolean setEmail(String email) {
		if (validateEmail(email)) {
			this.email = email;
			return true;
		}
		else
			return false;
	}
	public void		setEmail(JTextField textField) {
		if (setEmail(textField.getText()) == false) {
			EmployeeSendForm.setErrorBorder(textField);
		}
		else 
			EmployeeSendForm.setDefaultBorder(textField);
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public boolean setPhoneNumber(String phoneNumber) {
		if (validatePhone(phoneNumber)) {
			this.phoneNumber = phoneNumber;
			return true;
		}
		else
			return false;
	}

	public void		setPhoneNumber(JTextField textField) {
		if (setPhoneNumber(textField.getText()) == false) {
			EmployeeSendForm.setErrorBorder(textField);
		}
		else 
			EmployeeSendForm.setDefaultBorder(textField);
	}
}
