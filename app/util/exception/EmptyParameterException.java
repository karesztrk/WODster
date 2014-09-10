package util.exception;

public class EmptyParameterException extends Exception {

	/** Serial version. */
	private static final long serialVersionUID = -5235034466698058695L;
	
	private final Object parameter;
	
	public EmptyParameterException(Object parameter) {
		this.parameter = parameter;
	}

	public String getParameterName() {
		return parameter.toString();
	}
	
	

}
