package ecoexp.core.eco;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EcoProgramRepository extends CrudRepository<EcoProgram, Long> {

    List<EcoProgram> findByName(String name);

}
