package ecoexp.common.response;

import ecoexp.core.program.ProgramDTO;

public class ProgramResponse {
	public String code;
	public String name;
	public String theme;
	public String description;
	public String details;
	public String region;

	public ProgramResponse(ProgramDTO data) {
		this.code = data.getCode();
		this.name = data.getName();
		this.theme = data.getTheme();
		this.description = data.getDesc();
		this.details = data.getDetail();
		this.region = data.getRegion();
	}
}
