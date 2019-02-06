package com.boomi.flow.services.aws.polly;

import com.google.inject.AbstractModule;
import com.manywho.sdk.services.servers.EmbeddedServer;
import com.manywho.sdk.services.servers.undertow.UndertowServer;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.util.PortProvider;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

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
    protected EmbeddedServer startServer() throws Exception {
        EmbeddedServer server = new UndertowServer();

        server.addModule(injectModule());
        server.setApplication(Application.class);

        server.start("/api/aws/polly/1", 8080);

        return server;
    }

    protected static String createTestUrl(String path) {
        return String.format("http://%s:%d/api/aws/polly/1%s", PortProvider.getHost(), 8080, path);
    }

    protected String getResourceContent(String requestPathFile) throws IOException {
        InputStream stream = this.getClass().getResourceAsStream(requestPathFile);

        return IOUtils.toString(stream, Charset.forName("UTF-8"));
    }

    protected void assertExpectedBody(String pathExpectedBody, Response actualResponse) throws JSONException, IOException {
        JSONAssert.assertEquals(getResourceContent(pathExpectedBody), actualResponse.readEntity(String.class) , false);
    }
}
