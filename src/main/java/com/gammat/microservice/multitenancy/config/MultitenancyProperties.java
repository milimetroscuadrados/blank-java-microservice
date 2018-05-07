package com.gammat.microservice.multitenancy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by alejandro on 11/07/17.
 */
@Configuration
@ConfigurationProperties("spring.multitenancy")
public class MultitenancyProperties {

    @NestedConfigurationProperty
    private List<TenantDataSourceProperties> datasources;

    public List<TenantDataSourceProperties> getDatasources() {
        return datasources;
    }

    public void setDatasources(List<TenantDataSourceProperties> datasources) {
        this.datasources = datasources;
    }
}
