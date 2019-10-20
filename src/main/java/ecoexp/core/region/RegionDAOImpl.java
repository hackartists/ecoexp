package ecoexp.core.region;

import ecoexp.core.theme.ThemeDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Component
public class RegionDAOImpl implements RegionDAO {
	private Logger logger = LoggerFactory.getLogger(RegionDAOImpl.class);

	@Autowired
	RegionRepository regionRepository;

	@Override
	public boolean save(RegionDTO dto) throws IOException {
		logger.debug("In: save");
		regionRepository.save(dto);

		logger.debug("Out: save");
		return false;
	}

	@Override
	public List<RegionDTO> findByName(String name) {
		logger.debug("In: findByName");
		List<RegionDTO> res = regionRepository.findByName(name);
		logger.debug("Out: findByName");

		return res;
	}

	@Override
	public List<RegionDTO> findCodes() {
		logger.debug("In: findCodes");
		List<RegionDTO> res = regionRepository.findAllRegions();
		logger.debug("Out: findCodes");

		return res;
	}

	@Override
	public RegionDTO findById(Long regionId) {
		logger.debug("In: findByLinkedPrograms_RegionId");
		RegionDTO res = regionRepository.findById(regionId).get();
		logger.debug("Out: findByLinkedPrograms_RegionId");
		return res;
	}

//	@Override
//	public List<RegionDTO> findProgramsByRegionId(Long regionId) {
//		logger.debug("In: findByLinkedPrograms_RegionId");
//		List<RegionDTO> res = regionRepository.findProgramsByRegionId(regionId);
//		logger.debug("Size: {}, {}", res.size());
//		logger.debug("Out: findByLinkedPrograms_RegionId");
//		return res;
//	}


}
