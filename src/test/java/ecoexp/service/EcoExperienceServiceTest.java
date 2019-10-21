package ecoexp.service;

import ecoexp.common.request.CreateProgramRequest;
import ecoexp.common.request.KeywordQueryRequest;
import ecoexp.common.request.ListByRegionRequest;
import ecoexp.common.request.UpdateProgramRequest;
import ecoexp.common.response.*;
import ecoexp.core.program.ProgramDAO;
import ecoexp.core.program.ProgramDTO;
import ecoexp.core.program.ProgramRegionCountDTO;
import ecoexp.core.region.RegionDAO;
import ecoexp.core.region.RegionDTO;
import ecoexp.core.theme.ThemeDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class EcoExperienceServiceTest {
	@InjectMocks EcoExperienceService service= new EcoExperienceServiceImpl();
	@Mock private ProgramDAO programDAO;
	@Mock private RegionDAO regionDAO;

	@Before public void init() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test public void batch() throws Exception {
		String csv = "번호,프로그램명,테마별 분류,서비스 지역,프로그램 소개,프로그램 상세 소개\n" +
				"1,program,\"cat1,\",강원도 속초,description,details";
		when(programDAO.save(any(ProgramDTO.class))).thenReturn(true);

		EcoResponse res = service.batch(csv.getBytes());
		assertEquals((long)0, res.code.longValue());
		assertEquals(true, res.message);
	}

	@Test public void createProgram() throws Exception {
		CreateProgramRequest req = new CreateProgramRequest();
		req.name = "program name";
		req.detail = "detailed description";
		req.intro = "summary";
		req.theme="Cat1, Cat2";
		req.region="부산광역시 금정구";

		when(programDAO.save(any(ProgramDTO.class))).thenReturn(true);

		EcoResponse res = service.createProgram(req);
		assertEquals((long)0, res.code.longValue());
		assertEquals(true, res.message);

	}
	@Test public void updateProgram() throws Exception {
		UpdateProgramRequest req = new UpdateProgramRequest();
		req.name = "program name";
		req.detail = "detailed description";
		req.intro = "summary";
		req.theme="Cat1, Cat2";
		req.region="부산광역시 금정구";

		when(programDAO.update(any(ProgramDTO.class))).thenReturn(true);
		EcoResponse res = service.updateProgram(req);
		assertEquals((long)0, res.code.longValue());
		assertEquals(true, res.message);
	}

	@Test public void listRegions() throws Exception {
		List<RegionDTO> rd = new ArrayList<RegionDTO>();
		rd.add(getDefaultRegionDTO());
		when(regionDAO.findCodes()).thenReturn(rd);

		RegionCodeReponse res = service.listRegions();
		assertEquals(1, res.regions.size());
	}

	@Test public void listProgramsByRegionCode() throws Exception {
		List<RegionDTO> rd = new ArrayList<RegionDTO>();
		String regionCode = "reg123";
		String regionName = "평창군";

		rd.add(getDefaultRegionDTO());

		when(regionDAO.findProgramsByRegionCode(any(String.class))).thenReturn(rd);

		ProgramListResponse res = service.listProgramsByRegionCode("reg123");

		assertEquals(0, res.code.intValue());
		assertEquals(rd.get(0).getLinkedPrograms().size(), res.programs.size());
	}

	@Test public void listProgramsByRegion() throws Exception {
		ListByRegionRequest req = new ListByRegionRequest();
		req.region="평창군";

		RegionDTO data = getDefaultRegionDTO();

		when(regionDAO.findProgramsByName(any(String.class))).thenReturn(data);

		ListByRegionResponse res = service.listProgramsByRegion(req);

		assertEquals(data.getRegionCode(), res.region);
		assertEquals(data.getLinkedPrograms().size(), res.programs.size());
	}

	@Test public void countProgramByRegion() throws Exception {
		KeywordQueryRequest req = new KeywordQueryRequest();
		req.keyword="세계문화유산";

		List<ProgramRegionCountDTO> l = new ArrayList<>();
		l.add(getDefaultProgramRegionCountDTO());

		when(programDAO.countByRegion_Keyword(any(String.class))).thenReturn(l);

		CountProgramByRegionResponse res = service.countProgramsByRegion(req);

		assertEquals(req.keyword, res.keyword);
		assertEquals(l.size(), res.programs.size());
	}

	@Test public void countKeyword() throws Exception {
		KeywordQueryRequest req = new KeywordQueryRequest();
		req.keyword="문화";
		Long r = (long)57;

		when(programDAO.countKeyword(any(String.class))).thenReturn(r);

		KeywordFrequencyResponse res = service.countKeyword(req);
		assertEquals(req.keyword, res.keyword);
		assertEquals(r, res.count);
	}

	private ProgramRegionCountDTO getDefaultProgramRegionCountDTO() {
		ProgramRegionCountDTO res = new ProgramRegionCountDTO();
		res.setRegion("경상남도 경주시");
		res.setCount((long)2);

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
