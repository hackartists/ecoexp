package ecoexp.service;

import java.util.Iterator;
import java.util.List;
import java.io.IOException;

import ecoexp.common.request.CreateProgramRequest;
import ecoexp.common.response.ProgramListResponse;
import ecoexp.common.request.UpdateProgramRequest;
import ecoexp.core.region.RegionDAO;
import ecoexp.core.region.RegionDTO;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ecoexp.core.raw.EcoData;
import ecoexp.core.program.ProgramDTO;
import ecoexp.core.program.ProgramDAO;


import org.springframework.beans.factory.annotation.Autowired;
import ecoexp.common.response.*;

@Component
public class EcoExperienceServiceImpl implements EcoExperienceService {
	private Logger logger = LoggerFactory.getLogger(EcoExperienceService.class);

	@Autowired
	ProgramDAO programDAO;

	@Autowired
	RegionDAO regionDAO;

	@Override
	public EcoResponse batch(byte[] csv) {
		EcoResponse res = new EcoResponse();

		try {
			List<EcoData> programs = EcoData.parseFromCsv(new String(csv));
			logger.debug("Count: {}",programs.size());

			Iterator<EcoData> iter =  programs.iterator();

			while (iter.hasNext()) {
				programDAO.save(new ProgramDTO(iter.next()));
			}

			res.code=0;
			res.message=true;
		} catch (IOException e) {
			logger.error(e.getMessage());
			res = EcoErrors.BatchError;
		}

		return res;
	}

	@Override
	public KeywordResponse listByKeyword(String keyword) {
		logger.debug("In: listBytKeyword");
		List<ProgramDTO> data = programDAO.findByLinkedThemes_Name(keyword);
		logger.debug("Data size: {}", data.size());
		KeywordResponse res = new KeywordResponse();
		res.keyword = keyword;
		data.forEach(el->res.addProgram(el.getRegion(),1));
		logger.debug("Out: listByKeyword");

		return res;
	}

	@Override
	public EcoResponse createProgram(CreateProgramRequest req) {
		EcoResponse res = new EcoResponse();

		try {
			programDAO.save(new ProgramDTO(req));
			res.code=0;
			res.message=true;
		} catch (IOException e) {
			logger.error(e.getMessage());
			res = EcoErrors.CreateError;
		}

		return res;
	}

	@Override
	public EcoResponse updateProgram(UpdateProgramRequest req){
			return null;
	}

	@Override
	public RegionCodeReponse listRegions(){
		logger.debug("In: listRegions");
		RegionCodeReponse res = new RegionCodeReponse();
		regionDAO.findCodes().stream().forEach(res::addRegion);

		logger.debug("Out: listRegions");
		return res;
	}

//	@Override
//	public ProgramListResponse listProgramsByThemeId(Long themeId) {
//		logger.debug("In: listProgramsByThemeId");
//		ProgramListResponse res = new ProgramListResponse();
//		res.programs = programDAO.findByLinkedThemes_ThemeId(themeId);
//		logger.debug("Out: listProgramsByThemeId");
//
//		return res;
//	}

	@Override
	public ProgramListResponse listProgramsByRegionCode(String regionCode) {
		logger.debug("In: listProgramsByRegionId");
		ProgramListResponse res = new ProgramListResponse();
		// programDAO.findProgramsByRegionCode(regionCode).stream().forEach(res::addProgram);
		regionDAO.findProgramsByRegionCode(regionCode).stream().forEach(el->{
			el.getLinkedPrograms().forEach(res::addProgram);
		});

		logger.debug("Out: listProgramsByRegionId");

		return res;
	}

	@Override
	public ProgramListResponse listProgramsByRegion(String regionName) {
		logger.debug("In: listProgramsByRegion");
		ProgramListResponse res = new ProgramListResponse();
		regionDAO.findProgramsByName(regionName).forEach(el->el.getLinkedPrograms().forEach(res::addProgram));
		logger.debug("Out: listProgramsByRegion");

		return res;
	}
}
