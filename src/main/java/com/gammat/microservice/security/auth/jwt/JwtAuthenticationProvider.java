package com.gammat.microservice.security.auth.jwt;

import com.gammat.microservice.security.model.UserContext;
import com.gammat.microservice.security.model.token.RawAccessJwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

/**
 * Created by alejandro on 11/07/17.
 */
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final ResourceServerProperties jwtSettings;

    @Autowired
    public JwtAuthenticationProvider(ResourceServerProperties jwtSettings) {
        this.jwtSettings = jwtSettings;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();

        Jws<Claims> jwtClaims = rawAccessToken.parseClaims(jwtSettings.getJwt().getKeyValue());
        String username = jwtClaims.getBody().get("user_name", String.class);
        String firstName = jwtClaims.getBody().get("first_name", String.class);
        String lastName = jwtClaims.getBody().get("last_name", String.class);
        List<String> permissions = jwtClaims.getBody().get("authorities", List.class);
        List<GrantedAuthority> authorities = Optional.ofNullable(permissions)
							.orElseGet(Collections::emptyList)
							.stream()
							.map(authority -> new SimpleGrantedAuthority(authority))
							.collect(Collectors.toList());
        List<Integer> groups = jwtClaims.getBody().get("groups", List.class);

        UserContext context = UserContext.create(username, firstName, lastName, groups,  authorities);

        return new JwtAuthenticationToken(context, context.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}

