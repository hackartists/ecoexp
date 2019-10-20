package ecoexp.core.theme;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ThemeRepository extends CrudRepository<ThemeDTO, Long> {
    List<ThemeDTO> findByName(String name);

	@Query("SELECT t FROM ThemeDTO t join ProgramDTO p WHERE t.name like :keyword")
	List<ThemeDTO> findProgramsByKeyword(@Param("keyword") String keyword);
}
