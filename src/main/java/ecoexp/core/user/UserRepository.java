package ecoexp.core.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<UserDTO, Long> {
	UserDTO findByUsername(String username);
	boolean existsByUsername(String username);
	UserDTO findByUsernameAndPassword(String username, String password);
}
