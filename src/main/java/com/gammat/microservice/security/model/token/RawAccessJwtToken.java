package com.gammat.microservice.security.model.token;

import com.gammat.microservice.security.exceptions.JwtExpiredTokenException;
import io.jsonwebtoken.*;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by alejandro on 11/07/17.
 */
public class RawAccessJwtToken implements JwtToken {
    private static Logger logger = LoggerFactory.getLogger(RawAccessJwtToken.class);

    private String token;

    public RawAccessJwtToken(String token) {
        this.token = token;
    }

    /**
     * Parses and validates JWT Token signature.
     *
     * @throws BadCredentialsException
     * @throws JwtExpiredTokenException
     *
     */
    public Jws<Claims> parseClaims(String signingKey) {
        try {
            byte[] publicBytes = Base64.decodeBase64(
                    signingKey
                            .replace("-----BEGIN PUBLIC KEY-----", "")
                            .replace("-----END PUBLIC KEY-----", "")
                            .replace("\n", "")
            );
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(keySpec);
            return Jwts.parser().setSigningKey(pubKey).parseClaimsJws(this.token);

        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            logger.error("Invalid JWT Token", ex);
            throw new BadCredentialsException("Invalid JWT token: ", ex);
        } catch (ExpiredJwtException expiredEx) {
            logger.info("JWT Token is expired", expiredEx);
            throw new JwtExpiredTokenException(this, "JWT Token expired", expiredEx);
        } catch (NoSuchAlgorithmException e) {
            logger.info("Invalid Algorithm in JWT", e);
            throw new JwtExpiredTokenException(this, "Invalid Algorithm in JWT", e);
        } catch (InvalidKeySpecException e) {
            logger.info("Invalid public key for JWT", e);
            throw new JwtExpiredTokenException(this, "Invalid public key for JWT", e);
        }
    }

    @Override
    public String getToken() {
        return token;
    }
}