package exceptions;

public class BadResponseException extends Exception {
	
	private String message;
	
	public BadResponseException(String message) {
		this.message = message;
	}
	
	public String getMessage()
	{
		return message;
	}
}
