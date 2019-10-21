package ecoexp.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import ecoexp.common.request.CreateProgramRequest;
import ecoexp.common.request.KeywordQueryRequest;
import ecoexp.common.request.ListByRegionRequest;
import ecoexp.common.request.UpdateProgramRequest;
import ecoexp.common.response.*;
import ecoexp.core.program.ProgramDTO;
import ecoexp.core.program.ProgramRegionCountDTO;
import ecoexp.core.raw.EcoData;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import ecoexp.service.EcoExperienceService;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class EcoExperienceControllerTest {
	@Autowired private MockMvc mockMvc;
	@InjectMocks
	EcoExperienceController controller;

	@Mock
	private EcoExperienceService service;
	private static ObjectMapper objectMapper=new ObjectMapper();

	@Before public void init() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

	@Test public void batch() throws Exception {
		EcoResponse res = new EcoResponse();
		res.code = 0;
		res.message=true;
		when(service.batch(any(byte[].class))).thenReturn(res);
		MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "hello".getBytes(StandardCharsets.UTF_8));
		ResultActions resultActions = mockMvc.perform(
				MockMvcRequestBuilders.multipart("/v1/eco/batch").file(file));

		resultActions.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.code", is(res.code)))
				.andExpect(jsonPath("$.message", is(res.message)));
	}

	@Test public void batchIncorrectFieldName() throws Exception {
		EcoResponse res = new EcoResponse();
		res.code = 0;
		res.message=true;

		MockMultipartFile file = new MockMultipartFile("files", "filename.txt", "text/plain", "hello".getBytes(StandardCharsets.UTF_8));
		ResultActions resultActions = mockMvc.perform(
				MockMvcRequestBuilders.multipart("/v1/eco/batch").file(file));

		resultActions.andExpect(status().is(400));
	}

	@Test public void createData() throws Exception {
		CreateProgramRequest req = new CreateProgramRequest();
		req.name = "program name";
		req.detail = "detailed description";
		req.intro = "summary";
		req.theme="Cat1, Cat2";
		req.region="부산광역시 금정구";

		EcoResponse res = new EcoResponse();
		res.code = 0;
		res.message=true;
		when(service.createProgram(any(CreateProgramRequest.class))).thenReturn(res);

		ResultActions resultActions = mockMvc.perform(post("/v1/eco/create")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(req)))
				.andDo(print());

		resultActions.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.code", is(res.code)))
				.andExpect(jsonPath("$.message", is(res.message)));
	}

	@Test public void updateData() throws Exception {
		UpdateProgramRequest req = new UpdateProgramRequest();
		req.name = "program name";
		req.detail = "detailed description";
		req.intro = "summary";
		req.theme="Cat1, Cat2";
		req.region="부산광역시 금정구";

		EcoResponse res = new EcoResponse();
		res.code = 0;
		res.message=true;
		when(service.updateProgram(any(UpdateProgramRequest.class))).thenReturn(res);

		ResultActions resultActions = mockMvc.perform(post("/v1/eco/update/13")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(req)))
				.andDo(print());

		resultActions.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.code", is(res.code)))
				.andExpect(jsonPath("$.message", is(res.message)));
	}

	@Test public void listRegions() throws Exception {
		RegionCodeReponse res = new RegionCodeReponse();
		String regionCode = "reg123";
		String regionName = "테스트지역";
		res.addRegion(regionCode,regionName);

		when(service.listRegions()).thenReturn(res);

		ResultActions resultActions = mockMvc.perform(get("/v1/eco/list/regions")
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print());

		resultActions.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.regions", hasSize(1)))
				.andExpect(jsonPath("$.regions[0].regionCode", is(regionCode)))
				.andExpect(jsonPath("$.regions[0].name", is(regionName)));
	}

	@Test public void getData() throws Exception {
		ProgramListResponse res = new ProgramListResponse();
		res.code=0;
		EcoData d = new EcoData();
		d.detail="detail";
		d.intro="intro";
		d.name="name";
		d.region="평창군";
		d.theme="theme1,theme2";
		d.num=(long)13;
		ProgramDTO data = new ProgramDTO(d);
		res.addProgram(data);

		when(service.listProgramsByRegionCode(any(String.class))).thenReturn(res);

		ResultActions resultActions = mockMvc.perform(get("/v1/eco/region/reg123")
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print());

		resultActions.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.code", is(0)))
				.andExpect(jsonPath("$.programs", hasSize(1)))
				.andExpect(jsonPath("$.programs[0].id", is(data.getId().intValue())))
				.andExpect(jsonPath("$.programs[0].name", is(d.name)))
				.andExpect(jsonPath("$.programs[0].desc", is(d.intro)))
				.andExpect(jsonPath("$.programs[0].detail", is(d.detail)))
				.andExpect(jsonPath("$.programs[0].region", is(d.region)));
	}

	@Test public void listDataByRegion() throws Exception {
		ListByRegionRequest req = new ListByRegionRequest();
		req.region="평창군";

		ListByRegionResponse res = new ListByRegionResponse();
		res.region="reg123";
		EcoData d = new EcoData();
		d.detail="detail";
		d.intro="intro";
		d.name="name";
		d.region="평창군";
		d.theme="theme1,theme2";
		d.num=(long)13;
		ProgramDTO data = new ProgramDTO(d);
		res.addProgram(data);

		when(service.listProgramsByRegion(any(ListByRegionRequest.class))).thenReturn(res);

		ResultActions resultActions = mockMvc.perform(get("/v1/eco/list/region")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(req)))
				.andDo(print());

		resultActions.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.region", is(res.region	)))
				.andExpect(jsonPath("$.programs", hasSize(1)))
				.andExpect(jsonPath("$.programs[0].prgm_name", is(d.name)));
	}

	@Test public void countProgramsByRegion() throws Exception {
		KeywordQueryRequest req = new KeywordQueryRequest();
		req.keyword="세계문화유산";

		CountProgramByRegionResponse res = new CountProgramByRegionResponse();
		res.keyword= req.keyword;
		ProgramRegionCountDTO data = new ProgramRegionCountDTO();
		data.setCount((long)2);
		data.setRegion("경상남도 경주시");
		res.addCountProgram(data);

		when(service.countProgramsByRegion(any(KeywordQueryRequest.class))).thenReturn(res);

		ResultActions resultActions = mockMvc.perform(get("/v1/eco/list/keyword/count")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(req)))
				.andDo(print());

		resultActions.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.keyword", is(res.keyword)))
				.andExpect(jsonPath("$.programs", hasSize(1)))
				.andExpect(jsonPath("$.programs[0].region", is(data.getRegion())))
				.andExpect(jsonPath("$.programs[0].count", is(data.getCount().intValue())));
	}

	@Test public void countKeyword() throws Exception {
		KeywordQueryRequest req = new KeywordQueryRequest();
		req.keyword="문화";
		KeywordFrequencyResponse res = new KeywordFrequencyResponse();
		res.keyword = req.keyword;
		res.count=(long)59;

		when(service.countKeyword(any(KeywordQueryRequest.class))).thenReturn(res);

		ResultActions resultActions = mockMvc.perform(get("/v1/eco/count/keyword")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(req)))
				.andDo(print());

		resultActions.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.keyword", is(res.keyword)))
				.andExpect(jsonPath("$.count", is(res.count.intValue())));
	}

	// @Test public void recommend() throws Exception { }

}
