package ecoexp.core.region;

import ecoexp.core.program.ProgramDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegionRepository extends CrudRepository<RegionDTO, Long> {
    List<RegionDTO> findByName(String name);

    @Query("SELECT t FROM RegionDTO t")
    List<RegionDTO> findAllRegions();

    @Query("SELECT t FROM RegionDTO t join ProgramDTO p WHERE t.name like :name")
    List<RegionDTO> findProgramsByName(@Param("name") String name);

    @Query("SELECT r FROM RegionDTO r join fetch r.linkedPrograms WHERE r.regionCode like :regionCode")
    List<RegionDTO> findProgramsByRegionCode(@Param("regionCode") String regionCode);
}
