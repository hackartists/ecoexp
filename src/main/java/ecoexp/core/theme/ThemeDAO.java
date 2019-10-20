package ecoexp.core.theme;

import java.io.IOException;
import java.util.*;

public interface ThemeDAO {
	boolean save(ThemeDTO ecoProgram) throws IOException;
	List<ThemeDTO> findByName(String name);
	List<ThemeDTO> findProgramsByKeyword(String keyword);
}
