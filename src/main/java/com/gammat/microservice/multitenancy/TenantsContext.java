package com.gammat.microservice.multitenancy;

import com.gammat.microservice.multitenancy.config.MultitenancyProperties;
import com.gammat.microservice.multitenancy.config.TenantDataSourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alejandro on 12/07/17.
 */
@Configuration
@Scope("singleton")
public class TenantsContext {

    final public static String DEFAULT_TENANT = "1";

    @Autowired
    MultitenancyProperties multitenantDataSources;

    private Map<String, DataSource> map = new HashMap<>();

    public TenantsContext(){

    }

    @Bean(name = { "dataSource" })
    public DataSource dataSource() {
        return this.getDefault();
    }

    @PostConstruct
    public void load() {
        for (TenantDataSourceProperties dsp : multitenantDataSources.getDatasources()) {
            DataSourceBuilder factory = DataSourceBuilder
                    .create(dsp.getClassLoader())
                    .driverClassName(dsp.getDriverClassName())
                    .username(dsp.getUsername())
                    .password(dsp.getPassword())
                    .url(dsp.getUrl());
            map.put(dsp.getId(), factory.build());
        }
    }

    public DataSource get(String tenant) {
        return this.map.get(tenant);
    }

    public Map<String, DataSource> getAll() {
        return this.map;
    }

    public DataSource getDefault() {
        return this.get(DEFAULT_TENANT);
    }

}
