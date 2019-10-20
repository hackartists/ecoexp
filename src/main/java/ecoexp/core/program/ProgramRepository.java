package ecoexp.core.program;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProgramRepository extends CrudRepository<ProgramDTO, Long> {
    List<ProgramDTO> findByName(String name);
	List<ProgramDTO> findByLinkedThemes_Name(String themeName);
//    List<ProgramDTO> findByLinkedThemes_ThemeId(Long themeId);
//    List<ProgramDTO> findByLinkedRegions_Region_Code(String regionId);
}
