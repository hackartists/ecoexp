package ecoexp.core.theme;

import ecoexp.core.program.ProgramDTO;
import ecoexp.core.program.ProgramRegionCountDTO;
import ecoexp.core.region.RegionDTO;
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

import javax.swing.plaf.synth.Region;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertEquals;

public class ThemeDAOTest {
	@InjectMocks
	ThemeDAO dao = new ThemeDAOImpl();

	@Mock private ThemeRepository repo;

	@Before
	public void init() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test public void save() throws IOException {
		ThemeDTO data = getDefaultThemeDTO();
		when(repo.save(any(ThemeDTO.class))).thenReturn(null);

		assertEquals(true, dao.save(data));
	}

	private ThemeDTO getDefaultThemeDTO() {
		ThemeDTO r = new ThemeDTO();
		r.setThemeId((long)13);
		r.setName("테마파크");

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
