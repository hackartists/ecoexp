package ecoexp.core.region;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class RegionDAOTest {
	@Autowired RegionDAO dao;

	@Test public void save() throws Exception {}
	@Test public void findByName() throws Exception {}
	@Test public void findCodes() throws Exception {}
	@Test public void findProgramsByName() throws Exception {}
	@Test public void findById() throws Exception {}
	@Test public void findProgramsByRegionCode() throws Exception {}
}
