package com.example.app

import io.fluxzero.idp.testsupport.localstub.FluxzeroIdpStub
import io.fluxzero.proxy.ProxyServer
import io.fluxzero.sdk.configuration.ApplicationProperties
import io.fluxzero.testserver.TestServer
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import
import java.io.IOException
import java.net.ServerSocket

@SpringBootApplication
@Import(FluxzeroIdpStub::class)
class TestApp {

    companion object {
        private val log = LoggerFactory.getLogger(TestApp::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            // start Flux Test Server
            System.setProperty("FLUX_PORT", ApplicationProperties.getProperty("FLUX_PORT", "8888"))
            val fluxPort = ApplicationProperties.getIntegerProperty("FLUX_PORT")
            System.setProperty("FLUX_BASE_URL", "ws://localhost:$fluxPort")
            if (availablePort(fluxPort)) {
                TestServer.startServer()
            }

            // start Flux Proxy
            System.setProperty("PROXY_PORT", ApplicationProperties.getProperty("PROXY_PORT", "8080"))
            val proxyPort = ApplicationProperties.getIntegerProperty("PROXY_PORT")
            if (availablePort(proxyPort)) {
                ProxyServer.start()
            }


            // start application
            System.setProperty("FLUX_APPLICATION_NAME", ApplicationProperties.getProperty(
                "FLUX_APPLICATION_NAME", "Example"))
            val app = SpringApplication(TestApp::class.java)
            app.setAdditionalProfiles("main")
            app.run(*args)


            // initialize
            initializeApp()
            val localIdpIssuer = FluxzeroIdpStub.currentIssuer().orElse("not initialized")
            log.info("Application started successfully. Visit at: http://localhost:8080/ (local IDP: {})",
                localIdpIssuer)
        }

        @JvmStatic
        fun initializeApp() {
            //initialize the test application using commands etc.
        }

        @JvmStatic
        fun availablePort(port: Int): Boolean {
            return try {
                ServerSocket(port).use { serverSocket ->
                    serverSocket.reuseAddress = true
                    true
                }
            } catch (_: IOException) {
                false
            }
        }
    }
}
