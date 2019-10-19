package ecoexp.common.response;


public class EcoResponse {
	public Integer code;
	public Object message;

	public EcoResponse() {}
	public EcoResponse(int code, Object msg) {
		this.code = code;
		this.message = msg;
	}
}
