package ecoexp.service;

import ecoexp.common.response.TokenIssueResponse;
import ecoexp.common.request.AuthRequest;
import ecoexp.common.utils.JwtTokenUtil;
import com.google.common.hash.Hashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.charset.StandardCharsets;
import ecoexp.core.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import ecoexp.core.user.UserDAO;
import ecoexp.common.exception.EcoException;
import ecoexp.common.response.EcoResponse;
import org.springframework.stereotype.Component;
import ecoexp.common.utils.Generalizer;
import ecoexp.common.response.ErrorCode;

@Component
public class AuthenticationServiceImpl implements AuthenticationService {
	private Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

	@Autowired
	UserDAO userDAO;

	@Override
	public EcoResponse registerUser(AuthRequest req) {
		String jwt = JwtTokenUtil.generateToken(req.username);
		String pwHash = Generalizer.password(req.password);
		UserDTO user = new UserDTO(req.username, pwHash, jwt);
		EcoResponse res = null;

		try {
			userDAO.save(user);
			TokenIssueResponse ti = new TokenIssueResponse();
			ti.jwt = jwt;

			res = new EcoResponse();
			res.code=0;
			res.message=ti;
		} catch(EcoException e) {
			logger.error(e.getMessage());
			res = e.getEcoResponse();
		}

		return res;
	}

	@Override
	public EcoResponse validateUser(AuthRequest req){
		String pw = Generalizer.password(req.password);
		EcoResponse res = null;
		try {
			UserDTO user = userDAO.findByUsernameAndPassword(req.username, pw);
			res = new EcoResponse();
			res.code=0;
			TokenIssueResponse ti = new TokenIssueResponse();

			if (!JwtTokenUtil.validateToken(user.getJwt())) {
				user.setJwt(JwtTokenUtil.generateToken(user.getUsername()));
				userDAO.update(user);
			}

			ti.jwt = user.getJwt();
			res.message=ti;

		} catch(EcoException e) {
			logger.error(e.toString());
			res=e.getEcoResponse();
		}

		return res;
	}

	@Override
	public EcoResponse refreshToken(String token){
		logger.debug("In: refreshToken({})",token);
		EcoResponse res = null;
		String bearer="Bearer Token";
		if (!token.startsWith(bearer)) {
			res = new EcoResponse(ErrorCode.NoBearerTokenErrorCode, "no bearer token");
			logger.error(res.toString());
			return res;
		}

		token = token.replace(bearer, "");
		logger.debug("Unwrapped token: {}", token);
		if(!JwtTokenUtil.validateToken(token)) {
			res = new EcoResponse(ErrorCode.InvalidJwtErrorCode, "invalid JWT");
			logger.error(res.toString());
			return res;
		}

		try {
			String username = JwtTokenUtil.getClaim(token).getSubject();
			logger.debug("Decoded username: {}",username);
			UserDTO user = userDAO.findByUsername(username);
			String jwt = JwtTokenUtil.generateToken(username);
			user.setJwt(jwt);
			userDAO.update(user);
			res = new EcoResponse();
			res.code=0;
			TokenIssueResponse ti = new TokenIssueResponse();
			ti.jwt = user.getJwt();
			res.message=ti;
		} catch(EcoException e) {
			logger.error(e.toString());
			return e.getEcoResponse();
		}
		logger.debug("Out: refreshToken");

		return res;
	}
}
