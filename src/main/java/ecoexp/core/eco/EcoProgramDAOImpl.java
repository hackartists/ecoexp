package ecoexp.core.eco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class EcoProgramDAOImpl implements EcoProgramDAO {
	private Logger logger = LoggerFactory.getLogger(EcoProgramDAOImpl.class);

	@Autowired()
	EcoProgramRepository ecoProgramRepository;

	@Override
	public boolean save(EcoProgramDTO dto) throws IOException {
		logger.debug("In: save");
		ecoProgramRepository.save(dto);
		logger.debug("Out: save");
		return false;
	}
}
