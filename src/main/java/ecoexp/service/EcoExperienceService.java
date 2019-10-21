package ecoexp.service;

import ecoexp.common.request.CreateProgramRequest;
import ecoexp.common.response.ProgramListResponse;
import ecoexp.common.request.UpdateProgramRequest;
import ecoexp.common.response.EcoResponse;
import ecoexp.common.response.KeywordResponse;
import ecoexp.common.response.RegionCodeReponse;
import ecoexp.common.request.ListByRegionRequest;
import ecoexp.common.response.ListByRegionResponse;
import ecoexp.common.response.CountProgramByRegionResponse;
import ecoexp.common.request.KeywordQueryRequest;
import ecoexp.common.response.KeywordFrequencyResponse;

public interface EcoExperienceService {
	EcoResponse batch(byte[] csv);
	EcoResponse createProgram(CreateProgramRequest req);
	EcoResponse updateProgram(UpdateProgramRequest req);
	RegionCodeReponse listRegions();
	ProgramListResponse listProgramsByRegionCode(String regionCode);
	ListByRegionResponse listProgramsByRegion(ListByRegionRequest region);
	CountProgramByRegionResponse countProgramsByRegion(KeywordQueryRequest keyword);
	KeywordFrequencyResponse countKeyword(KeywordQueryRequest keyword);

}
