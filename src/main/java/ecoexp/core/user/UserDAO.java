package ecoexp.core.user;

import java.io.IOException;
import java.util.*;
import ecoexp.common.exception.EcoException;

public interface UserDAO {
	boolean save(UserDTO dto) throws EcoException;
	boolean update(UserDTO dto) throws EcoException;
	UserDTO findByUsernameAndPassword(String username, String password) throws EcoException;
}
