package com.rest.api.integrationtests.testcontainers;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

@ContextConfiguration(initializers = AbstractIntegrationTests.Initializer.class)
public class AbstractIntegrationTests {

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        static PostgreSQLContainer<?> postgreSql = new PostgreSQLContainer<>("postgres");

        private static void startContainers() {
            Startables.deepStart(Stream.of(postgreSql)).join();
        }

        private static Map<String,String> createConnectionConfiguration() {
            return Map.of(
                    "spring.datasource.url", postgreSql.getJdbcUrl(),
                    "spring.datasource.username", postgreSql.getUsername(),
                    "spring.datasource.password", postgreSql.getPassword()
            );
        }

        @SuppressWarnings({"unchecked", "rawtypes"})
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();
            ConfigurableEnvironment environment = applicationContext.getEnvironment();
            MapPropertySource testContainers = new MapPropertySource(
                    "testContainers", (Map) createConnectionConfiguration());
            environment.getPropertySources().addFirst(testContainers);
        }


    }
}
