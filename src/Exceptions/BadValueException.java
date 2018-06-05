package Exceptions;

public class BadValueException extends Exception {
	
	private String message;
	
	public BadValueException(String message)
	{
		this.message = message;
	}
	
	@Override
	public String getMessage()
	{
		return message;
	}
}
