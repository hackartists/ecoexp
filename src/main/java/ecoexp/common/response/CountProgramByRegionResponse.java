package ecoexp.common.response;

import java.util.*;
import ecoexp.core.program.ProgramRegionCountDTO;

class countProgramByRegion {
	public String region;
	public Long count;

	public countProgramByRegion(String region, Long count) {
		this.region = region;
		this.count = count;
	}
}

public class CountProgramByRegionResponse {
	public String keyword;
	public List<countProgramByRegion> programs=new ArrayList<countProgramByRegion>();

	public void addCountProgram(ProgramRegionCountDTO dto) {
		this.programs.add(new countProgramByRegion(dto.getRegion(), dto.getCount()));
	}
}
