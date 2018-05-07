package com.gammat.microservice.multitenancy.hibernate;

import com.gammat.microservice.multitenancy.TenantsContext;
import com.gammat.microservice.security.auth.jwt.JwtAuthenticationToken;
import com.gammat.microservice.security.model.UserContext;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created by alejandro on 11/07/17.
 */
@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        JwtAuthenticationToken jwt = ((JwtAuthenticationToken)SecurityContextHolder.getContext().getAuthentication());

        if(jwt != null){
            UserContext user = (UserContext) jwt.getPrincipal();
            return user.getGroups().get(0).toString(); //FIXME Y si tiene muchos?
        }else{
            return TenantsContext.DEFAULT_TENANT;
        }
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}