package br.com.alura.forum.dto;

public class TokenDto {

	private String token;
	private String type;

	public TokenDto(String token, String string) {
		this.token = token;
		this.type = string;
	}

	public String getToken() {
		return token;
	}

	public String getType() {
		return type;
	}

}
