package ecoexp.core.eco;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EcoProgramRepository extends CrudRepository<EcoProgramDTO, Long> {

    List<EcoProgramDTO> findByName(String name);

}
