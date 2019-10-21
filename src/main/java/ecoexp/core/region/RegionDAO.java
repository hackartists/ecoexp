package ecoexp.core.region;

import org.springframework.data.repository.query.Param;
import ecoexp.common.exception.EcoException;

import java.io.IOException;
import java.util.List;

public interface RegionDAO {
	boolean save(RegionDTO ecoProgram) throws IOException;
	List<RegionDTO> findByName(String name);
	List<RegionDTO> findCodes();
	RegionDTO findProgramsByName(String name) throws EcoException;
	RegionDTO findById(Long id);
	List<RegionDTO> findProgramsByRegionCode(String regionCode);
}
