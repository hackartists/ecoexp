package ecoexp.core.region;

import ecoexp.core.program.ProgramDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegionRepository extends CrudRepository<RegionDTO, String> {
    @Query("SELECT r FROM RegionDTO r")
    List<RegionDTO> findAllRegions();

    @Query("SELECT r FROM RegionDTO r join fetch r.linkedPrograms WHERE r.name like :name")
    List<RegionDTO> findProgramsByName(@Param("name") String name);

    @Query("SELECT r FROM RegionDTO r join fetch r.linkedPrograms WHERE r.regionCode like :regionCode")
    List<RegionDTO> findProgramsByRegionCode(@Param("regionCode") String regionCode);
}
