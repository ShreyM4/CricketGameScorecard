package com.cricket.scorecard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Entry Point for the Cricket Scorecard Engine Spring Boot Application.
 * 
 * DEVELOPER NOTES & ANNOTATIONS:
 * - @SpringBootApplication: This is a meta-annotation that combines three core Spring features:
 *   1. @Configuration: Indicates that this class provides Spring bean configurations.
 *   2. @EnableAutoConfiguration: Tells Spring Boot to automatically configure beans based on classpath dependencies (e.g., Spring Web, Spring Data MongoDB).
 *   3. @ComponentScan: Enables component scanning in 'com.cricket.scorecard' package to discover @RestController, @Service, @Repository, and @Component beans.
 */
@SpringBootApplication
public class ScorecardApplication {

    /**
     * Standard main method required to launch the Java application.
     * 
     * DEVELOPER NOTES:
     * - SpringApplication.run(): Launches the Spring application context, starts the embedded Tomcat web server (default port 8080),
     *   and initializes all Spring managed beans.
     *
     * @param args Command line arguments passed during application startup.
     */
    public static void main(String[] args) {
        SpringApplication.run(ScorecardApplication.class, args);
    }
}

