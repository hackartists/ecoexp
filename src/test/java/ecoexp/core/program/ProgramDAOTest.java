package	ecoexp.core.program;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class ProgramDAOTest {
	@Autowired ProgramDAO dao;

	@Test public void save() { }

	@Test public void update() { }

	@Test public void countByRegion_Keyword() { }

	@Test public void countKeyword() { }


}
