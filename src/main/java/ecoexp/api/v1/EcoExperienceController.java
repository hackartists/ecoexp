package ecoexp.api.v1;

import ecoexp.common.request.CreateProgramRequest;
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

    @PostMapping(path="/update", produces = "application/json")
    public EcoResponse updateData(@RequestBody UpdateProgramRequest req) {
        logger.debug("In: updateData()");
        logger.debug("Request Body: {}", req);
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

    @GetMapping(path="/region/{regionId}",produces = "application/json")
    public ProgramListResponse getData(@PathVariable String regionId) {
		logger.debug("In: getData({})",regionId);
		ProgramListResponse res = ecoExperienceService.listProgramsByRegionCode(regionId);
		logger.debug("Out: getData");
        return res;
    }

    @PostMapping(path="/udpate", produces = "application/json")
    public String updateData() {
		logger.debug("In: updateData");
		logger.debug("Out: updateData");
        return "updateData";
    }

    @GetMapping(path="/list/region", produces = "application/json")
    public String listDataByRegion() {
		logger.debug("In: listDataByRegion");
		logger.debug("Out: listDataByRegion");
        return "listDataByRegion";
    }

    @GetMapping(path="/list/keyword", produces = "application/json")
    public ResponseEntity<KeywordResponse> listDataByKeyword(String keyword) {
		logger.debug("In: listDataByKeyword");
		KeywordResponse res = ecoExperienceService.listByKeyword(keyword);
		logger.debug("Out: listDataByKeyword");
        return ResponseEntity.ok(res);
    }

    @GetMapping(path="/count/keyword", produces = "application/json")
    public String countDataByKeyword() {
		logger.debug("In: countDataByKeyword");
		logger.debug("Out: countDataByKeyword");
        return "listDataByKeyword";
    }

    // Optional
    @GetMapping(path="/recommend", produces = "application/json")
    public String recommend() {
		logger.debug("In: recommend");
		logger.debug("Out: recommend");
        return "recommend";
    }
}
