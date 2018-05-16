package actors;

public class InvalidCnpException extends Exception {
	private String	errorMessage;	
	
	public InvalidCnpException(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String	toString() {
		return "Eroare CNP invalid: " + errorMessage;
	}
}
