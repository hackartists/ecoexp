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
import org.springframework.web.bind.annotation.RequestHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin
@RestController
@RequestMapping("/v1/auth")
@Controller
public class AuthController {
	private Logger logger = LoggerFactory.getLogger(AuthController.class);

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

	@PostMapping(path="/refresh/token")
    public EcoResponse refresh(@RequestHeader("Authorization") String token) {
		logger.debug("In: refresh({})",token);
		EcoResponse res = authService.refreshToken(token);
        return res;
    }
}

