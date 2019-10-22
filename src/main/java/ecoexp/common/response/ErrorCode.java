package ecoexp.common.response;

public class ErrorCode {
	public static final int UnknownErrorCode = 1;

	public static final int BatchErrorCode = 100;
	public static final int CreateErrorCode = 101;
	public static final int UpdateErrorCode = 102;
	public static final int NotFoundErrorCode = 103;
	public static final int IOErrorCode=104;
	public static final int UserExistErrorCode = 105;
	public static final int LoginFailedErrorCode = 106;
	public static final int NoBearerTokenErrorCode = 107;
	public static final int InvalidJwtErrorCode=108;
	public static final int EncryptFailedErrorCode=109;
	public static final int DecryptFailedErrorCode=110;

	public static final int UnknownSearchStrategyCode=200;
}
