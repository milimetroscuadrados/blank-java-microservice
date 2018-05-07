package com.gammat.microservice.multitenancy.config;

import com.gammat.microservice.multitenancy.TenantsContext;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by alejandro on 12/07/17.
 */
@Configuration
public class HibernateConfig {

    @Autowired
    private JpaProperties jpaProperties;

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }


    @PersistenceContext
    @Primary
    @Bean
    @DependsOn("flyway")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                       MultiTenantConnectionProvider multiTenantConnectionProviderImpl,
                                                                       CurrentTenantIdentifierResolver currentTenantIdentifierResolverImpl) {
        Map<String, Object> properties = new HashMap<>();
        properties.putAll(jpaProperties.getHibernateProperties(dataSource));
        properties.put(Environment.MULTI_TENANT, MultiTenancyStrategy.DATABASE);
        properties.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProviderImpl);
        properties.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolverImpl);

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.workia");
        em.setJpaVendorAdapter(jpaVendorAdapter());
        em.setJpaPropertyMap(properties);
        em.afterPropertiesSet();
        return em;
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return new Flyway() {
            @Autowired
            private TenantsContext dbs;

            final Logger logger = Logger.getLogger("FlywayIntegrator");

            @Override
            public int migrate() throws FlywayException {
                logger.log(Level.INFO, "Starting Flyway Migration");

                // Point it to the database
                for (DataSource ds : dbs.getAll().values()) {
                    // Create the Flyway instance
                    Flyway flyway = new Flyway();
                    try {
                        flyway.setDataSource(ds);

                        // Start the migration
                        flyway.migrate();
                    } catch (Exception e) {
                        logger.log(Level.SEVERE, "Error while migrating:", e);
                    }
                    logger.log(Level.INFO, "Finished Flyway Migration");
                }

                return 0;
            }
        };
    }
}