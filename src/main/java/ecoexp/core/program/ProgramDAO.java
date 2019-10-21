package ecoexp.core.program;

import java.io.IOException;
import java.util.*;

public interface ProgramDAO {
	boolean save(ProgramDTO ecoProgram) throws IOException;
	List<ProgramDTO> findByName(String name);
	List<ProgramDTO> findByLinkedThemes_Name(String themeName);
	List<ProgramDTO> findProgramsByRegionCode(String regionCode);
}
