package ecoexp.core.region;

import ecoexp.common.exception.EcoException;

import java.io.IOException;
import java.util.List;

public interface RegionDAO {
	boolean save(RegionDTO ecoProgram) throws IOException;
	List<RegionDTO> findCodes();
	RegionDTO findProgramsByName(String name) throws EcoException;
	List<RegionDTO> findProgramsByRegionCode(String regionCode);
}
