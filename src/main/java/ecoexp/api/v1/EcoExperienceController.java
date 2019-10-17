package ecoexp.api.v1;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/eco")
public class EcoExperienceController {
    // @GET
    // @Path("hello/{name}")
    // @Consumes("application/vnd.asimio-v1+json")
    // @Produces("application/vnd.asimio-v1+json")
    // @ApiOperation(value = "Gets a hello resource. World Version 1 (version in Accept Header)", response = Hello.class)
    // @ApiResponses(value = {
    //         @ApiResponse(code = 200, message = "Hello resource found"),
    //         @ApiResponse(code = 404, message = "Hello resource not found")
    //     })

    @PostMapping(produces = "application/json")
    @RequestMapping({ "/batch" })
    public String batch() {
        return "batch";
    }

    @PostMapping(produces = "application/json")
    @RequestMapping({ "/create" })
    public String createData() {
        return "createData";
    }

    @GetMapping(produces = "application/json")
    @RequestMapping({ "/{regionId}" })
    public String getData(@PathVariable String regionId) {
        return "getData";
    }

    @GetMapping(produces = "application/json")
    @RequestMapping({ "/update" })
    public String updateData() {
        return "updateData";
    }

    @GetMapping(produces = "application/json")
    @RequestMapping({ "/list/region" })
    public String listDataByRegion() {
        return "listDataByRegion";
    }

    @GetMapping(produces = "application/json")
    @RequestMapping({ "/list/keyword" })
    public String listDataByKeyword() {
        return "listDataByKeyword";
    }

    @GetMapping(produces = "application/json")
    @RequestMapping({ "/count/keyword" })
    public String countDataByKeyword() {
        return "listDataByKeyword";
    }

    // Optional
    @GetMapping(produces = "application/json")
    @RequestMapping({ "/recommend" })
    public String recommend() {
        return "";
    }
}

