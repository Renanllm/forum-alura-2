package br.com.alura.forum.config.validator;

public class ErrorDto {

	private String field;
	private String message;

	public ErrorDto(String field, String message) {
		super();
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}

}
