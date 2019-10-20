package ecoexp.common.response;


public class EcoErrors {
	public static final EcoResponse BatchError = new EcoResponse(100, "Failed to insert data into database");
	public static final EcoResponse CreateError = new EcoResponse(101, "Failed to create data");
}
