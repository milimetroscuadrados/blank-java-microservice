package com.gammat.microservice.security.auth.jwt.verifier;

import org.springframework.stereotype.Component;

/**
 * Created by alejandro on 11/07/17.
 */
@Component
public class BloomFilterTokenVerifier implements TokenVerifier {
    @Override
    public boolean verify(String jti) {
        return true;
    }
}
