package ecoexp.common.utils;

import java.util.*;
import ecoexp.common.request.AuthRequest;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenUtil {

	public static final long Validity = 5 * 60 * 60;
	private static final String secret = "secret";

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
		Claims claims = getClaim(token);
		final String username = claims.getSubject();
		final Date expiration = claims.getExpiration();

		Boolean res = new Date().before(expiration);

		return res;
	}
}
