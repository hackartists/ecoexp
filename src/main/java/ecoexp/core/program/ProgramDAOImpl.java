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


@Component
public class ProgramDAOImpl implements ProgramDAO {
	private Logger logger = LoggerFactory.getLogger(ProgramDAOImpl.class);

	@Autowired
	ProgramRepository programRepository;

	@Override
	public boolean save(ProgramDTO dto) throws IOException {
		logger.debug("In: save");
		// for (ThemeDTO el: dto.getLinkedThemes()) {
		// 	themeDAO.save(el);
		// }
		programRepository.save(dto);

		logger.debug("Out: save");
		return false;
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

//	@Override
//    public List<ProgramDTO> findByLinkedThemes_ThemeId(Long themeId) {
//	    logger.debug("In: findByLinkedThemes_ThemeId");
//	    List<ProgramDTO> res = programRepository.findByLinkedThemes_ThemeId(themeId);
//	    logger.debug("Out: findByLinkedThemes_ThemeId");
//
//	    return res;
//    }

    @Override
    public List<ProgramDTO> findProgramsByRegionCode(String regionCode) {
        logger.debug("In: findByLinkedRegions_RegionCode");
        List<ProgramDTO> res = programRepository.findProgramsByRegionCode(regionCode);
        logger.debug("Out: findByLinkedRegions_RegionCode");

        return res;
    }
}
