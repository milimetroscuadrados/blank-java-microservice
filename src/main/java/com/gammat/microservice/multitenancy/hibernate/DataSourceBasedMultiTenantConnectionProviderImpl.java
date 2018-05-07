package com.gammat.microservice.multitenancy.hibernate;

import com.gammat.microservice.multitenancy.TenantsContext;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by alejandro on 11/07/17.
 */
@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    @Autowired
    TenantsContext context;

    @Override
    protected DataSource selectAnyDataSource() {
        return context.getDefault();
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        return context.get(tenantIdentifier);
    }
}
