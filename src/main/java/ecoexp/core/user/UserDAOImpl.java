package ecoexp.core.user;

import ecoexp.core.region.RegionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ecoexp.core.theme.ThemeRepository;
import ecoexp.core.theme.ThemeDAO;
import ecoexp.core.theme.ThemeDTO;
import java.util.*;
import ecoexp.common.exception.EcoException;
import ecoexp.common.response.ErrorCode;
import java.math.BigInteger;
import ecoexp.common.utils.CryptoUtil;


@Component
public class UserDAOImpl implements UserDAO {
	private Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

	@Autowired
	UserRepository userRepository;

	private  void _save(UserDTO dto) throws EcoException  {
		logger.debug("In: _save");
		logger.debug("Original JWT: {}", dto.getJwt());
		dto.setJwt(CryptoUtil.encrypt(dto.getJwt()));
		logger.debug("Encrypted JWT: {}", dto.getJwt());
		userRepository.save(dto);
		dto.setJwt(CryptoUtil.decrypt(dto.getJwt()));
		logger.debug("Decrypted JWT: {}", dto.getJwt());
		logger.debug("Out: _save");
	}

	@Override
	public boolean save(UserDTO dto) throws EcoException {
		logger.debug("In: save");

		if (userRepository.existsByUsername(dto.getUsername())) {
			logger.error("User({}) already exist", dto.getUsername());
			throw new EcoException(ErrorCode.UserExistErrorCode, String.format("User(%s) already exist", dto.getUsername()));
		}
		_save(dto);

		logger.debug("Out: save");
		return true;
	}

	@Override
	public boolean update(UserDTO dto) throws EcoException {
		logger.debug("In: update");

		if (!userRepository.existsByUsername(dto.getUsername())) {
			logger.error("User({}) does not exist", dto.getUsername());
			throw new EcoException(ErrorCode.UpdateErrorCode, String.format("User(%s) does not exist", dto.getUsername()));
		}

		_save(dto);
		logger.debug("Out: update");

		return true;
	}

	@Override
	public UserDTO findByUsernameAndPassword(String username, String password) throws EcoException {
		UserDTO res = userRepository.findByUsernameAndPassword(username, password);
		if (res == null) {
			throw new EcoException(ErrorCode.LoginFailedErrorCode, "Incorrect username or password");
		}

		res.setJwt(CryptoUtil.decrypt(res.getJwt()));

		return res;
	}

	@Override
	public UserDTO findByUsername(String username) throws EcoException {
		logger.debug("In: findByUsername({})", username);
		UserDTO res = userRepository.findByUsername(username);
		logger.debug("User Jwt(DTO): {}", res.getJwt());
		if (res == null) {
			throw new EcoException(ErrorCode.LoginFailedErrorCode, "Incorrect username or password");
		}

		res.setJwt(CryptoUtil.decrypt(res.getJwt()));
		logger.debug("Out: findByUsername()");
		return res;
	}

	@Override
	public boolean existsByUsernameAndJwt(String username, String jwt) {
		return userRepository.existsByUsernameAndJwt(username, jwt);
	}
}
