package com.gammat.microservice.multitenancy.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

/**
 * Created by alejandro on 11/07/17.
 */
public class TenantDataSourceProperties extends DataSourceProperties {
    /**
     * Id of the tenant of the datasource.
     */
    private String id = "tenant-id";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
