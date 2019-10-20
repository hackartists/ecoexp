package ecoexp.common.response;

import ecoexp.core.program.ProgramDTO;

import java.util.ArrayList;
import java.util.List;

class programResponse {
    public String name;
    public String detail;
    public String desc;
    public String region;
    public String theme;

    public programResponse(ProgramDTO dto) {
        this.name = dto.getName();
        this.detail = dto.getDetail();
        this.desc = dto.getDesc();
        this.region = dto.getRegion();
        List<String> strs = new ArrayList<String>();
        dto.getLinkedThemes().forEach(el->strs.add(el.getName()));
        this.theme = String.join(",",strs);
    }
}

public class ProgramListResponse {
    public List<programResponse> programs=new ArrayList<programResponse>();

    public void addProgram(ProgramDTO programDTO) {
        this.programs.add(new programResponse(programDTO));
    }
}
