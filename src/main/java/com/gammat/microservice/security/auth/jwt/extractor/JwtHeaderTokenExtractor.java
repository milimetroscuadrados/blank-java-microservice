package com.gammat.microservice.security.auth.jwt.extractor;

import com.gammat.microservice.security.auth.jwt.JwtAuthenticationToken;
import com.gammat.microservice.security.model.token.RawAccessJwtToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by alejandro on 12/07/17.
 */
@Component
public class JwtHeaderTokenExtractor extends BearerTokenExtractor {

    @Override
    public Authentication extract(HttpServletRequest request) {
        String tokenValue = extractToken(request);
        if (tokenValue != null) {
            return new JwtAuthenticationToken(new RawAccessJwtToken(tokenValue));
        }
        return null;
    }
}
