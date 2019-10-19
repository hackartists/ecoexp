package ecoexp.service;

import java.util.Iterator;
import java.util.List;
import java.io.IOException;
import ecoexp.core.eco.EcoProgramDAOImpl;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ecoexp.core.eco.EcoData;
import ecoexp.core.eco.EcoProgramDTO;
import ecoexp.core.eco.EcoProgramDAO;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class EcoExperienceServiceImpl implements EcoExperienceService {
	private Logger logger = LoggerFactory.getLogger(EcoExperienceService.class);

	@Autowired
	EcoProgramDAO ecoProgramDAO;

	@Override
	public boolean batch(byte[] csv) throws IOException {
		List<EcoData> programs = EcoData.parseFromCsv(new String(csv));
		logger.debug("Count: {}",programs.size());

		Iterator<EcoData> iter =  programs.iterator();

		while (iter.hasNext()) {
			ecoProgramDAO.save(new EcoProgramDTO(iter.next()));
		}

		return true;
	}
}
