package ecoexp.core.program;

import java.io.IOException;
import java.util.*;
import ecoexp.common.exception.EcoException;

public interface ProgramDAO {
	boolean save(ProgramDTO ecoProgram);
	boolean update(ProgramDTO ecoProgram) throws EcoException;
	List<ProgramRegionCountDTO> countByRegion_Keyword(String keyword);
	Long countKeyword(String keyword);
	ProgramDTO findByCode(String code);
}
