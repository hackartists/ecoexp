package ecoexp.service;

import ecoexp.common.response.TokenIssueResponse;
import ecoexp.common.request.AuthRequest;
import ecoexp.common.response.EcoResponse;


public interface AuthenticationService {
	EcoResponse registerUser(AuthRequest req);
	EcoResponse validateUser(AuthRequest req);
	EcoResponse refreshToken();
}
