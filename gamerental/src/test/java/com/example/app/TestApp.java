package com.example.app;

import io.fluxzero.proxy.ProxyServer;
import io.fluxzero.sdk.configuration.ApplicationProperties;
import io.fluxzero.testserver.TestServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.ServerSocket;

import static io.fluxzero.sdk.configuration.ApplicationProperties.getProperty;

@SpringBootApplication
@Slf4j
public class TestApp {
    public static void main(String[] args) {
        // start Flux Test Server
        System.setProperty("FLUX_PORT", ApplicationProperties.getProperty("FLUX_PORT", "8888"));
        int fluxPort = ApplicationProperties.getIntegerProperty("FLUX_PORT");
        System.setProperty("FLUX_BASE_URL", "ws://localhost:" + fluxPort);
        if (availablePort(fluxPort)) {
            TestServer.main(new String[0]);
        }

        // start Flux Proxy
        System.setProperty("PROXY_PORT", ApplicationProperties.getProperty("PROXY_PORT", "8080"));
        int proxyPort = ApplicationProperties.getIntegerProperty("PROXY_PORT");
        if (availablePort(proxyPort)) {
            ProxyServer.main(new String[0]);
        }

        // start application
        System.setProperty("FLUX_APPLICATION_NAME", getProperty("FLUX_APPLICATION_NAME", "GameRental"));
        SpringApplication app = new SpringApplication(App.class);
        app.setAdditionalProfiles("main");
        app.run(args);
        log.info("GameRental started successfully");
    }

    static boolean availablePort(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setReuseAddress(true);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}