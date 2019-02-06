package com.boomi.flow.services.aws.polly.description;

import com.boomi.flow.services.aws.polly.BaseTest;
import com.manywho.sdk.services.servers.EmbeddedServer;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.json.JSONException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class DescriptionTest extends BaseTest {
    private static EmbeddedServer server;

    @Before
    public void startUp() throws Exception {
        server = startServer();
    }

    @After
    public void tearDown() {
        server.stop();
    }

    @Test
    public void testFirsMetadataCall() throws JSONException, IOException {
        String url = createTestUrl("/metadata");
        Entity<String> entity = Entity.entity("{}", MediaType.APPLICATION_JSON_TYPE);

        Response response = new ResteasyClientBuilder().build()
                .target(url)
                .request()
                .post(entity);

        Assert.assertEquals(200, response.getStatus());
        assertExpectedBody("/descriptions/without-parameters-response.json", response);
        response.close();
    }

    @Test
    public void testSecondMetadataCall() throws JSONException, IOException {
        String url = createTestUrl("/metadata");
        Entity<String> entity = Entity.entity(getResourceContent("/descriptions/with-parameters-request.json"), MediaType.APPLICATION_JSON_TYPE);

        Response response = new ResteasyClientBuilder().build()
                .target(url)
                .request()
                .post(entity);

        Assert.assertEquals(200, response.getStatus());
        assertExpectedBody("/descriptions/with-parameters-response.json", response);

        response.close();
    }
}
