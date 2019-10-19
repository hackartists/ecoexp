package ecoexp.api.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ecoexp.service.EcoExperienceService;
import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import ecoexp.common.response.EcoResponse;
import ecoexp.common.response.EcoErrors;

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
    public ResponseEntity<EcoResponse> batch( @RequestParam("file") MultipartFile file) {
		logger.debug("In: batch");
		EcoResponse res = new EcoResponse();

		try {
			res.code=0;
			res.message=ecoExperienceService.batch(file.getBytes());
		} catch (IOException e) {
			res = EcoErrors.BatchError;
		}

		logger.debug("Out: batch");
        return ResponseEntity.ok(res);
    }

    @PostMapping(path="/create", produces = "application/json")
    public String createData() {
		logger.debug("In: createData()");
		logger.debug("Out: createData()");
        return "createData";
    }

    @GetMapping(path="/region{regionId}",produces = "application/json")
    public String getData(@PathVariable String regionId) {
		logger.debug("In: getData");
		logger.debug("Out: getData");
        return "getData";
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
    public String listDataByKeyword() {
		logger.debug("In: listDataByKeyword");
		logger.debug("Out: listDataByKeyword");
        return "listDataByKeyword";
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
