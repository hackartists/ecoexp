package ecoexp.core.program;

import java.io.IOException;
import java.util.*;

public interface ProgramDAO {
	boolean save(ProgramDTO ecoProgram) throws IOException;
	List<ProgramDTO> findByName(String name);
	List<ProgramDTO> findByLinkedThemes_Name(String themeName);
//	List<ProgramDTO> findByLinkedThemes_ThemeId(Long themeId);
//	List<ProgramDTO> findByLinkedRegions_Region_Code(String regionId);
}
