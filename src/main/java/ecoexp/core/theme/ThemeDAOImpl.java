package ecoexp.core.theme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

@Component
public class ThemeDAOImpl implements ThemeDAO {
	private Logger logger = LoggerFactory.getLogger(ThemeDAOImpl.class);

	@Autowired
	ThemeRepository themeRepository;

	@Override
	public boolean save(ThemeDTO dto) throws IOException {
		logger.debug("In: save");
		themeRepository.save(dto);
		logger.debug("Out: save");
		return true;
	}

	@Override
	public List<ThemeDTO> findByName(String name) {
		logger.debug("In: findByName");
		List<ThemeDTO> res = themeRepository.findByName(name);
		logger.debug("Result: {}", res);
		logger.debug("Out: findByName");

		return res;
	}

	@Override
	public List<ThemeDTO> findProgramsByKeyword(String keyword) {
		logger.debug("In: findProgramsByKeyword");
		List<ThemeDTO> res = themeRepository.findProgramsByKeyword(String.format("%%%s%%",keyword));
		logger.debug("Out: findProgramsByKeyword");

		return res;
	}
}
