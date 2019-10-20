package ecoexp.service;

import ecoexp.common.request.CreateProgramRequest;
import ecoexp.common.response.ProgramListResponse;
import ecoexp.common.request.UpdateProgramRequest;
import ecoexp.common.response.EcoResponse;
import ecoexp.common.response.KeywordResponse;
import ecoexp.common.response.RegionCodeReponse;

public interface EcoExperienceService {
	EcoResponse batch(byte[] csv);
	KeywordResponse listByKeyword(String name);
	EcoResponse createProgram(CreateProgramRequest req);
	EcoResponse updateProgram(UpdateProgramRequest req);
	RegionCodeReponse listRegions();
//	ProgramListResponse listProgramsByThemeId(Long themeId);
	ProgramListResponse listProgramsByRegionCode(String regionId);

}
