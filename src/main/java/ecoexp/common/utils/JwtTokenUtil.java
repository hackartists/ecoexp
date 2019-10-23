package ecoexp.common.utils;

import java.util.*;
import ecoexp.common.request.AuthRequest;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import ecoexp.core.user.UserDAO;
import javax.annotation.PostConstruct;
import ecoexp.core.user.UserDTO;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtTokenUtil {
	private static Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

	public static final long Validity = 5 * 60 * 60;
	private static final String secret = "secret";

	@Autowired
	UserDAO dao;

	static UserDAO userDAO = null;

	@PostConstruct
	private void initStaticDao () {
		if (userDAO == null) {
			userDAO = this.dao;
		}
	}

	public static Claims getClaim(String token) {
		Claims res = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

		return res;
	}

	public static String generateToken(String subject) {
		Map<String, Object> claims = new HashMap<>();

		Date now = new Date(System.currentTimeMillis());
		Date expiration = new Date(System.currentTimeMillis() + Validity * 1000);

		String res = Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(now)
			.setExpiration(expiration)
			.signWith(SignatureAlgorithm.HS512, secret).compact();

		return res;
	}

	public static Boolean validateToken(String token) {
		logger.debug("In: validateToken({})", token);
		Boolean res=false;
		try {
			JwtTokenUtil util = new JwtTokenUtil();
			util.initStaticDao();

			Claims claims = getClaim(token);
			String username = claims.getSubject();
			Date expiration = claims.getExpiration();
			String encToken = CryptoUtil.encrypt(token);
			res= new Date().before(expiration) && userDAO.existsByUsernameAndJwt(username,encToken);
		} catch(Exception e) {
			logger.error(e.toString());
			return false;
		}

		logger.debug("Out: validateToken");
		return res;
	}
}
