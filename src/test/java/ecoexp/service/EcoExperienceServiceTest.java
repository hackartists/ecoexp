package ecoexp.service;

import ecoexp.core.program.ProgramDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class EcoExperienceServiceTest {
	@InjectMocks EcoExperienceService service;
	@Mock private ProgramDAO programDAO;

	@Before public void init() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test public void batch() throws Exception {

	}
	@Test public void listByKeyword() throws Exception { }
	@Test public void createProgram() throws Exception { }
	@Test public void updateProgram() throws Exception { }
	@Test public void listRegions() throws Exception { }
	@Test public void listProgramsByRegionCode() throws Exception { }
	@Test public void listProgramsByRegion() throws Exception { }
	@Test public void countProgramByRegion() throws Exception { }
	@Test public void countKeyword() throws Exception { }
}
