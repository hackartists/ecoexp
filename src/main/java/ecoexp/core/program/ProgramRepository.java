package ecoexp.core.program;

import ecoexp.core.region.RegionDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import javax.persistence.*;

import java.util.List;

public interface ProgramRepository extends CrudRepository<ProgramDTO, Long> {
    List<ProgramDTO> findByName(String name);
	List<ProgramDTO> findByLinkedThemes_Name(String themeName);
//    List<ProgramDTO> findByLinkedThemes_ThemeId(Long themeId);

    @Query(value="select p from ProgramDTO p join RegionDTO r where r.regionCode = :regionCode")
    List<ProgramDTO> findProgramsByRegionCode(@Param("regionCode") String regionCode);



	@Query(value="SELECT region, COUNT(*) as count FROM programs WHERE programs.description like :keyword GROUP BY region", nativeQuery=true)
	List<Object[]> countByRegion_Keyword(@Param("keyword") String keyword);

	@Query(value="call word_freq(:keyword)", nativeQuery=true)
	List<Object[]> countKeyword(@Param("keyword") String keyword);
}
