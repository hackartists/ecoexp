package ecoexp.core.program;

import ecoexp.core.region.RegionDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProgramRepository extends CrudRepository<ProgramDTO, Long> {
    List<ProgramDTO> findByName(String name);
	List<ProgramDTO> findByLinkedThemes_Name(String themeName);
//    List<ProgramDTO> findByLinkedThemes_ThemeId(Long themeId);

    @Query(value="select p from ProgramDTO p join RegionDTO r where r.regionCode = :regionCode")
    List<ProgramDTO> findProgramsByRegionCode(@Param("regionCode") String regionCode);
}
