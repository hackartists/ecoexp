package ecoexp.core.region;

import ecoexp.core.program.ProgramDTO;
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

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class RegionDAOTest {
	@InjectMocks
	RegionDAO dao = new RegionDAOImpl();

	@Mock RegionRepository repo;

	@Before
	public void init() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test public void save() throws Exception {
		RegionDTO data = getDefaultRegionDTO();
		when(repo.save(any(RegionDTO.class))).thenReturn(null);

		assertEquals(true, dao.save(data));
	}

	@Test public void findCodes() throws Exception {
		List<RegionDTO> expected = getDefaultRegionDTOList();
		when(repo.findAllRegions()).thenReturn(expected);

		List<RegionDTO> actual = dao.findCodes();
		assertEquals(expected.size(),actual.size());
		assertEquals(expected.get(0).hashCode(), actual.get(0).hashCode());
	}

	@Test public void findProgramsByName() throws Exception {
		List<RegionDTO> expected = getDefaultRegionDTOList();
		when(repo.findProgramsByName(any(String.class))).thenReturn(expected);

		RegionDTO actual = dao.findProgramsByName("평창군");
		assertEquals(expected.get(0).hashCode(),actual.hashCode());
	}

	@Test public void findProgramsByRegionCode() throws Exception {
		List<RegionDTO> expected = getDefaultRegionDTOList();
		when(repo.findProgramsByRegionCode(any(String.class))).thenReturn(expected);

		List<RegionDTO> actual = dao.findProgramsByRegionCode("reg123");
		assertEquals(expected.size(),actual.size());
		assertEquals(expected.hashCode(),actual.hashCode());
	}

	private List<RegionDTO> getDefaultRegionDTOList() {
		List<RegionDTO> res = new ArrayList<>();
		res.add(getDefaultRegionDTO());

		return res;
	}

	private RegionDTO getDefaultRegionDTO() {
		RegionDTO r = new RegionDTO();
		r.setRegionCode("reg123");
		r.setName("평창군");
		r.setId((long)13);

		Set<ProgramDTO> hs = new HashSet<>();
		hs.add(getDefaultProgramDTO());
		r.setLinkedPrograms(hs);

		return r;
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
