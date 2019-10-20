package ecoexp.core.region;

import org.springframework.data.repository.query.Param;

import java.io.IOException;
import java.util.List;

public interface RegionDAO {
	boolean save(RegionDTO ecoProgram) throws IOException;
	List<RegionDTO> findByName(String name);
	List<RegionDTO> findCodes();
	// List<RegionDTO> findByRegionId(Long regionId);
	RegionDTO findById(Long id);
//	List<RegionDTO> findProgramsByRegionId(Long regionId);
}
