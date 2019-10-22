package ecoexp.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ecoexp.common.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthzFilter extends BasicAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthzFilter.class);
	private static final String TOKEN_HEADER="Authorization";

    public JwtAuthzFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
		logger.debug("In: doFilterInternal");
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if (authentication == null) {
			logger.error("Authentication failed");
            filterChain.doFilter(request, response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
		logger.debug("Out: doFilterInternal");
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(JwtAuthzFilter.TOKEN_HEADER);
        if (StringUtils.isNotEmpty(token) && JwtTokenUtil.validateToken(token)) {
			return new UsernamePasswordAuthenticationToken(JwtTokenUtil.getClaim(token).getSubject(), null, null);
        }

        return null;
    }
}
