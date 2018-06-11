package com.broctagon.exchangeadmin.filter;

import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {

	
	private static final String SECRET_KEY = "secretKey";
	
	private static final String HEADER = "Auth";
	
	public static void addAuthentication(HttpServletResponse response, String userName) {
		String jwt = Jwts.builder().setSubject(userName)
				.setExpiration(new Date(System.currentTimeMillis() + 1000))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.compact();
		response.setHeader(HEADER, jwt);
	}
	
	public static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER);
		if (!StringUtils.isEmpty(token)) {
			String user = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
			return StringUtils.isEmpty(user) ? null : new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
		}
		return null;
	}
}
