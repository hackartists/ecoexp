package ecoexp.common.response;

import ecoexp.core.program.ProgramDTO;
import java.util.*;


class shortProgramResponse {
	public String prgm_name;
	public String theme;

	public shortProgramResponse(String name, String theme) {
		this.prgm_name = name;
		this.theme = theme;
	}
}

public class ListByRegionResponse {
	public String region;
	public List<shortProgramResponse> programs=new ArrayList<shortProgramResponse>();

	public ListByRegionResponse() { }

	public ListByRegionResponse(String regionCode) {
		this.region=regionCode;
	}

	public void addProgram(ProgramDTO program) {
		List<String> themes = new ArrayList<String>();
		program.getLinkedThemes().forEach(el->themes.add(el.getName()));
		this.programs.add(new shortProgramResponse(program.getName(), String.join(",",themes)));
	}

}
