package ecoexp.service;

import java.io.IOException;

import ecoexp.core.eco.EcoData;
import ecoexp.core.eco.EcoProgramDTO;
import ecoexp.core.eco.EcoProgramDAO;

public interface EcoExperienceService {
	boolean batch(byte[] csv) throws IOException;
}
