package ecoexp.core.program;

import ecoexp.core.region.RegionDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import javax.persistence.*;

import java.util.List;

public interface ProgramRepository extends CrudRepository<ProgramDTO, Long> {
	ProgramDTO findByCode(String code);

	@Query(value="SELECT region, COUNT(*) as count FROM programs WHERE programs.description like :keyword GROUP BY region", nativeQuery=true)
	List<Object[]> countByRegion_Keyword(@Param("keyword") String keyword);

	@Query(value="call word_freq(:keyword)", nativeQuery=true)
	List<Object[]> countKeyword(@Param("keyword") String keyword);
}
