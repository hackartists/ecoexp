package ecoexp.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class RootController {

    @GetMapping(produces = "application/json")
    @RequestMapping({ "/version" })
    public String version() {
        return "0.1.0";
    }
}
