package com.gammat.microservice.security.auth.jwt.verifier;

/**
 * Created by alejandro on 11/07/17.
 */
public interface TokenVerifier {
    public boolean verify(String jti);
}
