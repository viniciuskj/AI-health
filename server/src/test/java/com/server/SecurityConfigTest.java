package com.server;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import static org.junit.jupiter.api.Assertions.*;
import com.server.configurations.SecurityConfig;

@SpringBootTest
public class SecurityConfigTest {

    @Autowired
    private SecurityConfig securityConfig;

    @Test
    public void testCorsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = (UrlBasedCorsConfigurationSource) securityConfig.corsConfigurationSource();
        CorsConfiguration config = source.getCorsConfigurations().get("/**");

        assertNotNull(config, "CorsConfiguration should not be null");

        assertNotNull(config.getAllowedOrigins(), "Allowed origins should not be null");
        assertTrue(config.getAllowedOrigins().contains("*"), "Expected '*' in allowed origins");
        assertNotNull(config.getAllowedMethods(), "Allowed methods should not be null");
        assertTrue(config.getAllowedMethods().containsAll(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")), "Expected HTTP methods are not set correctly");
        assertNotNull(config.getAllowedHeaders(), "Allowed headers should not be null");
        assertTrue(config.getAllowedHeaders().containsAll(Arrays.asList("Authorization", "Cache-Control", "Content-Type")), "Expected HTTP headers are not set correctly");
        assertEquals(Boolean.TRUE, config.getAllowCredentials(), "Expected 'allowCredentials' to be true");
    }
}
