package com.gammat.microservice.security.auth.jwt.extractor;

import com.gammat.microservice.security.auth.jwt.JwtAuthenticationToken;
import com.gammat.microservice.security.model.token.RawAccessJwtToken;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


/**
 * Created by alejandro on 11/07/17.
 */
//@Component
public class JwtCookieTokenExtractor implements TokenExtractor {
    private final static Log logger = LogFactory.getLog(JwtCookieTokenExtractor.class);

    //public static String HEADER_PREFIX = "Bearer ";
    private static final String AUTH_COOKIE_NAME = "X-AUTH-TOKEN";

    public Authentication extract(HttpServletRequest request) {
        String tokenValue = extractToken(request);
        if (tokenValue != null) {
            return new JwtAuthenticationToken(new RawAccessJwtToken(tokenValue));
        }
        return null;
    }

    protected String extractToken(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, AUTH_COOKIE_NAME);

        String token = (cookie != null ? cookie.getValue() : null);

        // bearer type allows a request parameter as well
        if (token == null) {
            logger.debug("Token not found in headers. Trying request parameters.");
            token = request.getParameter(OAuth2AccessToken.ACCESS_TOKEN);
            if (token == null) {
                logger.debug("Token not found in request parameters.  Not an OAuth2 request.");
            }
            else {
                request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_TYPE, OAuth2AccessToken.BEARER_TYPE);
            }
        }

        return token;
    }
}
