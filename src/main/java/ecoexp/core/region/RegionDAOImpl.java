package ecoexp.core.region;

import ecoexp.core.theme.ThemeDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import ecoexp.common.utils.Generalizer;
import ecoexp.common.exception.EcoException;
import ecoexp.common.response.ErrorCode;

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
	public List<RegionDTO> findCodes() {
		logger.debug("In: findCodes");
		List<RegionDTO> res = regionRepository.findAllRegions();
		logger.debug("Out: findCodes");

		return res;
	}

	@Override
	public List<RegionDTO> findProgramsByRegionCode(String regionCode) {
		logger.debug("In: findByLinkedPrograms_RegionId");
		List<RegionDTO> res = regionRepository.findProgramsByRegionCode(regionCode);
		logger.debug("Size: {}", res.size());
		logger.debug("Out: findByLinkedPrograms_RegionId");

		return res;
	}

	@Override
	public RegionDTO findProgramsByName(String name) throws EcoException {
		logger.debug("In: findProgramsByName");
		List<RegionDTO> res = regionRepository.findProgramsByName(Generalizer.region(name));
		logger.debug("Out: findProgramsByName");
		if (res.size() < 1) {
			throw new EcoException(ErrorCode.NotFoundErrorCode, String.format("Not found data with region name ({})", name));
		}

		return res.get(0);
	}
}
