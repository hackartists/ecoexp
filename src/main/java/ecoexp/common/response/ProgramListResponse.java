package ecoexp.common.response;

import ecoexp.core.program.ProgramDTO;

import java.util.ArrayList;
import java.util.List;

class _programResponse {
	public Long id;
    public String name;
    public String detail;
    public String desc;
    public String region;
    public String theme;
	public String code;

    public _programResponse(ProgramDTO dto) {
		this.id = dto.getId();
        this.name = dto.getName();
        this.detail = dto.getDetail();
        this.desc = dto.getDesc();
        this.region = dto.getRegion();
		this.code =dto.getCode();
        List<String> strs = new ArrayList<String>();
        dto.getLinkedThemes().forEach(el->strs.add(el.getName()));
        this.theme = String.join(",",strs);
    }
}

public class ProgramListResponse {
	public Integer code=0;
    public List<_programResponse> programs=new ArrayList<_programResponse>();

    public void addProgram(ProgramDTO programDTO) {
        this.programs.add(new _programResponse(programDTO));
    }
}
