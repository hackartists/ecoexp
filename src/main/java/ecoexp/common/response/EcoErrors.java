package ecoexp.common.response;


public class EcoErrors {
	public static final EcoResponse BatchError = new EcoResponse(ErrorCode.BatchErrorCode, "Failed to insert data into database");
	public static final EcoResponse CreateError = new EcoResponse(ErrorCode.CreateErrorCode, "Failed to create data");
}
