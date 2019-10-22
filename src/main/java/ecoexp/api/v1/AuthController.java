package ecoexp.api.v1;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ecoexp.common.response.TokenIssueResponse;
import ecoexp.common.request.AuthRequest;
import org.springframework.web.bind.annotation.RequestBody;
import ecoexp.common.response.EcoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import ecoexp.service.AuthenticationService;
import org.springframework.stereotype.Controller;

@CrossOrigin
@RestController
@RequestMapping("/v1/auth")
@Controller
public class AuthController {
	@Autowired
	private AuthenticationService authService;

    @PostMapping(path="/signup",produces = "application/json")
    public EcoResponse signUp(@RequestBody AuthRequest req) {
		EcoResponse res = authService.registerUser(req);
        return res;
    }

    @PostMapping(path="/signin", produces = "application/json")
    public EcoResponse signIn(@RequestBody AuthRequest req) {
		EcoResponse res = authService.validateUser(req);
        return res;
    }

	@PostMapping(path="/refresh/token",produces = "application/json")
    public EcoResponse refresh() {
		EcoResponse res = authService.refreshToken();
        return res;
    }
}

