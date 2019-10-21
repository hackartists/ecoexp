package	ecoexp.core.program;

import ecoexp.common.exception.EcoException;
import ecoexp.core.region.RegionDTO;
import ecoexp.core.theme.ThemeDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ProgramDAOTest {
	@InjectMocks
	ProgramDAO dao = new ProgramDAOImpl();

	@Mock private ProgramRepository repo;

	@Before
	public void init() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test public void save() {
		ProgramDTO data = getDefaultProgramDTO();
		when(repo.save(any(ProgramDTO.class))).thenReturn(null);

		assertEquals(true, dao.save(data));
	}

	@Test public void update() throws Exception {
		ProgramDTO data = getDefaultProgramDTO();
		when(repo.save(any(ProgramDTO.class))).thenReturn(null);
		when(repo.existsById(any(Long.class))).thenReturn(true);

		assertEquals(true, dao.update(data));
	}

	@Test(expected = EcoException.class)
	public void updateNoDocument() throws Exception {
		ProgramDTO data = getDefaultProgramDTO();
		when(repo.save(any(ProgramDTO.class))).thenReturn(null);
		when(repo.existsById(any(Long.class))).thenReturn(false);
		dao.update(data);
	}

	@Test public void countByRegion_Keyword() {
		String keyword = "%세계문화유산%";
		List<ProgramRegionCountDTO> expected = new ArrayList<>();
		expected.add(getDefaultProgramRegionCountDTO());
		List<Object[]> r = new ArrayList<>();
		Object el[]={"경상남도 경주시", new BigInteger("2")};
		r.add(el);

		when(repo.countByRegion_Keyword(any(String.class))).thenReturn(r);

		List<ProgramRegionCountDTO> actual = dao.countByRegion_Keyword(keyword);

		assertEquals("경상남도 경주시",actual.get(0).getRegion());
		assertEquals((long)2, actual.get(0).getCount().longValue());
	}

	@Test public void countKeyword() {
		String keyword="문화";
		List<Object[]> r = new ArrayList<>();
		Object el[]={(int)59};
		r.add(el);
		when(repo.countKeyword(keyword)).thenReturn(r);

		Long actual = dao.countKeyword(keyword);
		assertEquals((long)59,actual.longValue());
	}

	private ProgramRegionCountDTO getDefaultProgramRegionCountDTO() {
		ProgramRegionCountDTO res = new ProgramRegionCountDTO();
		res.setRegion("경상남도 경주시");
		res.setCount((long)2);

		return res;
	}

	private ProgramDTO getDefaultProgramDTO() {
		ProgramDTO pd = new ProgramDTO();
		pd.setId((long)13);
		pd.setDesc("Desc");
		pd.setDetail("detail");
		pd.setName("name");
		pd.setRegion("region");

		ThemeDTO td = new ThemeDTO();
		td.setName("theme");
		td.setThemeId((long)1);
		Set<ThemeDTO> hs = new HashSet<ThemeDTO>();
		hs.add(td);

		RegionDTO r = new RegionDTO();
		r.setRegionCode("reg123");
		r.setName("평창군");
		r.setId((long)13);
		Set<RegionDTO> rd = new HashSet<RegionDTO>();
		rd.add(r);

		pd.setLinkedThemes(hs);
		pd.setLinkedRegions(rd);

		return pd;
	}

}
