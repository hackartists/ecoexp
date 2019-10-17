package ecoexp.api.v1;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping(produces = "application/json")
    @RequestMapping({ "/signup" })
    public String signUp() {
        return "signup";
    }

    @PostMapping(produces = "application/json")
    @RequestMapping({ "/signin" })
    public String signIn() {
        return "signin";
    }
}

