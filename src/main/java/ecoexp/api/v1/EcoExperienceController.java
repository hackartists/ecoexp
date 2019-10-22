package ecoexp.api.v1;

import ecoexp.common.request.CreateProgramRequest;
import ecoexp.common.request.ListByRegionRequest;
import ecoexp.common.response.ProgramListResponse;
import ecoexp.common.request.UpdateProgramRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ecoexp.service.EcoExperienceService;
import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import ecoexp.common.response.*;
import ecoexp.common.request.KeywordQueryRequest;
import ecoexp.common.request.RecommendRequest;

@CrossOrigin
@RestController
@RequestMapping("/v1/eco")
@Controller
public class EcoExperienceController {
	private Logger logger = LoggerFactory.getLogger(EcoExperienceController.class);

	@Autowired
	private EcoExperienceService ecoExperienceService;

    @PostMapping(path= "/batch", produces = "application/json")
    public EcoResponse batch( @RequestParam("file") MultipartFile file) {
		logger.debug("In: batch");
        EcoResponse res;
		try {
            res = ecoExperienceService.batch(file.getBytes());
        }catch(IOException e) {
		    res=EcoErrors.BatchError;
        }
		logger.debug("Out: batch");
        return res;
    }

    @PostMapping(path="/create", produces = "application/json")
    public EcoResponse createData(@RequestBody CreateProgramRequest req) {
		logger.debug("In: createData()");
		logger.debug("Request Body: {}", req);
        EcoResponse res = ecoExperienceService.createProgram(req);
		logger.debug("Out: createData()");

        return res;
    }

    @PostMapping(path="/update/{programId}", produces = "application/json")
    public EcoResponse updateData(@PathVariable Long programId, @RequestBody UpdateProgramRequest req) {
        logger.debug("In: updateData()");
		logger.debug("Path Variable: {}", programId);
        logger.debug("Request Body: {}", req);
		req.id = programId;
        EcoResponse res = ecoExperienceService.updateProgram(req);
        logger.debug("Out: updateData()");

        return res;
    }

    @GetMapping(path="/list/regions", produces = "application/json")
    public RegionCodeReponse listRegions() {
        logger.debug("In: listRegions()");
        RegionCodeReponse res = ecoExperienceService.listRegions();
        logger.debug("Out: listRegions()");

        return res;
    }

	@GetMapping(path="/program/{prgCode}",produces = "application/json")
    public ProgramResponse getProgram(@PathVariable String prgCode) {
		logger.debug("In: getData({})",prgCode);
		ProgramResponse res = ecoExperienceService.getProgramByCode(prgCode);
		logger.debug("Out: getData");
        return res;
    }

    @GetMapping(path="/region/{regionCode}",produces = "application/json")
    public ProgramListResponse getData(@PathVariable String regionCode) {
		logger.debug("In: getData({})",regionCode);
		ProgramListResponse res = ecoExperienceService.listProgramsByRegionCode(regionCode);
		logger.debug("Out: getData");
        return res;
    }

    @GetMapping(path="/list/region", consumes = "application/json", produces = "application/json")
    public ListByRegionResponse listDataByRegion(@RequestBody ListByRegionRequest region) {
		logger.debug("In: listDataByRegion({})", region.region);
		ListByRegionResponse res = ecoExperienceService.listProgramsByRegion(region);
		logger.debug("Out: listDataByRegion");
        return res;
    }

	@GetMapping(path="/list/keyword/count", produces = "application/json")
    public CountProgramByRegionResponse countProgramsByRegion(@RequestBody KeywordQueryRequest req) {
		logger.debug("In: countDataByKeyword");
		CountProgramByRegionResponse res = ecoExperienceService.countProgramsByRegion(req);
		logger.debug("Out: countDataByKeyword");
        return res;
    }

	@GetMapping(path="/count/keyword", produces = "application/json")
    public KeywordFrequencyResponse countKeyword(@RequestBody KeywordQueryRequest req) {
		logger.debug("In: countKeyword");
		KeywordFrequencyResponse res = ecoExperienceService.countKeyword(req);
		logger.debug("Out: countKeyword");
        return res;
    }

    @GetMapping(path="/recommend", produces = "application/json")
    public RecommendResponse recommend(@RequestBody RecommendRequest req) {
		logger.debug("In: recommend({},{})", req.region, req.keyword);
		RecommendResponse res = ecoExperienceService.recommendPlace(req);
		logger.debug("Out: recommend");
        return res;
    }
}
