package com.boomi.flow.services.aws.polly;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import com.google.inject.AbstractModule;
import com.manywho.sdk.api.jackson.ObjectMapperFactory;
import org.jboss.resteasy.util.PortProvider;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BaseTest {

    protected static ObjectMapper objectMapper = ObjectMapperFactory.create();

    /**
     * start the server without database speed up the tests
     */
    protected StoppableUndertowServer startServer() {
        StoppableUndertowServer server = new StoppableUndertowServer();
        server.addModule(new AbstractModule() {
            @Override
            protected void configure() {

            }
        });

        server.setApplication(Application.class);

        try {
            server.start("/api/aws/polly/1", 8080);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return server;
    }

    protected static String testUrl(String path) {
        return String.format("http://%s:%d/api/aws/polly/1%s", PortProvider.getHost(), 8080, path);
    }

    protected static String getResourceContent(String requestPathFile) throws IOException, URISyntaxException {
        return new String(Files.readAllBytes(Paths.get(Resources.getResource(requestPathFile).toURI())));
    }

}
