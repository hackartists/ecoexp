package ecoexp.service;

import java.util.Iterator;
import java.util.List;
import java.io.IOException;
import ecoexp.common.exception.EcoException;
import ecoexp.common.request.ListByRegionRequest;
import ecoexp.core.program.ProgramRegionCountDTO;
import ecoexp.common.request.KeywordQueryRequest;

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
	public EcoResponse createProgram(CreateProgramRequest req) {
		EcoResponse res = new EcoResponse();

		programDAO.save(new ProgramDTO(req));
		res.code=0;
		res.message=true;

		return res;
	}

	@Override
	public EcoResponse updateProgram(UpdateProgramRequest req){
		logger.debug("In: updateProgram");
		EcoResponse res = new EcoResponse();
		try {
			res.code=0;
			res.message = programDAO.update(new ProgramDTO(req));
		} catch (EcoException e) {
			res = e.getEcoResponse();
		}

		logger.debug("Out: updateProgram");
		return res;
	}

	@Override
	public RegionCodeReponse listRegions(){
		logger.debug("In: listRegions");
		RegionCodeReponse res = new RegionCodeReponse();
		regionDAO.findCodes().stream().forEach(res::addRegion);

		logger.debug("Out: listRegions");
		return res;
	}

	@Override
	public ProgramListResponse listProgramsByRegionCode(String regionCode) {
		logger.debug("In: listProgramsByRegionId");
		ProgramListResponse res = new ProgramListResponse();
		regionDAO.findProgramsByRegionCode(regionCode).stream().forEach(el->{
			el.getLinkedPrograms().forEach(res::addProgram);
		});

		logger.debug("Out: listProgramsByRegionId");

		return res;
	}

	@Override
	public ListByRegionResponse listProgramsByRegion(ListByRegionRequest region) {
		logger.debug("In: listProgramsByRegion");
		String regionName = region.region;
		ListByRegionResponse res = new ListByRegionResponse();
		try {
			RegionDTO data = regionDAO.findProgramsByName(regionName);
			res.region = data.getRegionCode();
			data.getLinkedPrograms().forEach(res::addProgram);
		} catch (EcoException e) {
			logger.error(e.getMessage());
			res = null;
		}

		logger.debug("Out: listProgramsByRegion");

		return res;
	}

	@Override
	public CountProgramByRegionResponse countProgramsByRegion(KeywordQueryRequest keyword) {
		logger.debug("In: countProgramsByRegion({})", keyword);
		CountProgramByRegionResponse res = new CountProgramByRegionResponse();
		res.keyword = keyword.keyword;
		programDAO.countByRegion_Keyword(keyword.keyword).forEach(res::addCountProgram);
		logger.debug("Out: countProgramsByRegion");
		return res;
	}

	@Override
	public KeywordFrequencyResponse countKeyword(KeywordQueryRequest keyword) {
		logger.debug("In: countKeyword");
		KeywordFrequencyResponse res = new KeywordFrequencyResponse();
		res.keyword = keyword.keyword;
		res.count = programDAO.countKeyword(keyword.keyword);
		logger.debug("Out: countKeyword");

		return res;
	}
}
