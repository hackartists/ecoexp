package ecoexp.core.program;

import ecoexp.core.region.RegionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ecoexp.core.theme.ThemeRepository;
import ecoexp.core.theme.ThemeDAO;
import ecoexp.core.theme.ThemeDTO;
import java.util.*;
import ecoexp.common.exception.EcoException;
import ecoexp.common.response.ErrorCode;
import java.math.BigInteger;


@Component
public class ProgramDAOImpl implements ProgramDAO {
	private Logger logger = LoggerFactory.getLogger(ProgramDAOImpl.class);

	@Autowired
	ProgramRepository programRepository;

	@Override
	public boolean save(ProgramDTO dto) {
		logger.debug("In: save");
		programRepository.save(dto);
		logger.debug("Out: save");
		return false;
	}

	@Override
	public boolean update(ProgramDTO ecoProgram) throws EcoException {
		logger.debug("In: update");
		if (!programRepository.existsById(ecoProgram.getId())) {
			logger.error("Document({}) does not exist", ecoProgram.getId());
			throw new EcoException(ErrorCode.UpdateErrorCode, String.format("Document({}) does not exist", ecoProgram.getId()));
		}
		programRepository.save(ecoProgram);
		logger.debug("Out: update");

		return true;
	}

	@Override
	public List<ProgramDTO> findByName(String name) {
		logger.debug("In: findByName");
		List<ProgramDTO> res = programRepository.findByName(name);
		logger.debug("Out: findByName");

		return res;
	}

	@Override
	public List<ProgramDTO> findByLinkedThemes_Name(String themeName){
		logger.debug("In: findByLinkedThemes_Name");
		List<ProgramDTO> res =  programRepository.findByLinkedThemes_Name(themeName);
		logger.debug("Out: findByLinkedThemes_Name");

		return res;
	}

	@Override
	public List<ProgramRegionCountDTO> countByRegion_Keyword(String keyword) {
		logger.debug("In: countByRegion_Keyword({})",keyword);
		String qry = String.format("%%%s%%",keyword);
		logger.debug("Query string: {}", qry);
		List<ProgramRegionCountDTO> res = new ArrayList<ProgramRegionCountDTO>();
		List<Object[]> data = programRepository.countByRegion_Keyword(qry);
		data.forEach(el->{
				logger.debug("{}:{}",el[0],el[1]);
				res.add(new ProgramRegionCountDTO(el));
			});
		logger.debug("Out: countByRegion_Keyword");

		return res;
	}

	@Override
	public Long countKeyword(String keyword) {
		List<Object[]> data = programRepository.countKeyword(keyword);
		if ( data.size() <= 0) {
			return (long)0;
		}

		return (long)((Integer) data.get(0)[0]);
	}
}