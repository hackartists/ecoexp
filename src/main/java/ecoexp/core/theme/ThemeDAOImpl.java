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
}
