package ecoexp.common.exception;

import ecoexp.common.response.EcoResponse;


public class EcoException extends Exception {
	private int code;

	public EcoException(Exception e) {
		super(e.getMessage());
	}

	public EcoException(int code, Exception e) {
		super(e.getMessage());
		this.code = code;
	}

	public EcoException(int code, String message) {
		super(message);
		this.code=code;
	}

	public EcoResponse getEcoResponse() {
		return new EcoResponse(code, this.getMessage());
	}

	public final int getCode() {
		return code;
	}

	public final void setCode(final int code) {
		this.code = code;
	}

}
