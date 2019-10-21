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

@CrossOrigin
@RestController
@RequestMapping("/v1/eco")
@Controller
public class EcoExperienceController {
	private Logger logger = LoggerFactory.getLogger(EcoExperienceController.class);

	@Autowired
	private EcoExperienceService ecoExperienceService;

    // @GET
    // @Path("hello/{name}")
    // @Consumes("application/vnd.asimio-v1+json")
    // @Produces("application/vnd.asimio-v1+json")
    // @ApiOperation(value = "Gets a hello resource. World Version 1 (version in Accept Header)",
    // response = Hello.class)
    // @ApiResponses(value = {
    //         @ApiResponse(code = 200, message = "Hello resource found"),
    //         @ApiResponse(code = 404, message = "Hello resource not found")
    //     })

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

    @GetMapping(path="/list/keyword", produces = "application/json")
    public ResponseEntity<KeywordResponse> listDataByKeyword(String keyword) {
		logger.debug("In: listDataByKeyword");
		KeywordResponse res = ecoExperienceService.listByKeyword(keyword);
		logger.debug("Out: listDataByKeyword");
        return ResponseEntity.ok(res);
    }

    // Optional
    @GetMapping(path="/recommend", produces = "application/json")
    public String recommend() {
		logger.debug("In: recommend");
		logger.debug("Out: recommend");
        return "recommend";
    }
}
