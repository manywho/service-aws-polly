package com.boomi.flow.services.aws.polly;

import com.google.inject.AbstractModule;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.util.PortProvider;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

public class BaseTest {

    protected AbstractModule injectModule() {
        return new AbstractModule() {
            @Override
            protected void configure() {

            }
        };
    }

    /**
     * start the server without database speed up the tests
     */
    protected StoppableUndertowServer startServer() {
        StoppableUndertowServer server = new StoppableUndertowServer();

        server.addModule(injectModule());
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

    protected String getResourceContent(String requestPathFile) {
        try {
            InputStream stream = this.getClass().getResourceAsStream(getFromResource(requestPathFile));
            return IOUtils.toString(stream);

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    protected void assertExpectedBody(String pathExpectedBody, Response actualResponse) {
        try {
            JSONAssert.assertEquals(getResourceContent(pathExpectedBody), actualResponse.readEntity(String.class) , true);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private String getFromResource(String path) {
        if (path.startsWith("/")) {
            return path;
        } else {
            return "/" + path;
        }
    }
}
