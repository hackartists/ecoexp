package ecoexp.core.region;

import org.springframework.data.repository.query.Param;

import java.io.IOException;
import java.util.List;

public interface RegionDAO {
	boolean save(RegionDTO ecoProgram) throws IOException;
	List<RegionDTO> findByName(String name);
	List<RegionDTO> findCodes();
	List<RegionDTO> findProgramsByName(String name);
	RegionDTO findById(Long id);
	List<RegionDTO> findProgramsByRegionCode(String regionCode);
}
