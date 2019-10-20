package ecoexp.core.region;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegionRepository extends CrudRepository<RegionDTO, Long> {
    List<RegionDTO> findByName(String name);

    @Query("SELECT t FROM RegionDTO t")
    List<RegionDTO> findAllRegions();

//    @Query("SELECT t FROM RegionDTO t join ProgramDTO p WHERE t.region_id = :regionId")
//    List<RegionDTO> findProgramsByRegionId(@Param("regionId") String regionCode);
}
