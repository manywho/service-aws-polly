package com.boomi.flow.services.aws.polly.description;

import com.boomi.flow.services.aws.polly.BaseTest;
import com.boomi.flow.services.aws.polly.StoppableUndertowServer;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URISyntaxException;

public class DescriptionTest extends BaseTest {
    @Test
    public void testFirsMetadataCall() throws IOException, JSONException, URISyntaxException {

        StoppableUndertowServer server = startServer();
        String url = testUrl("/metadata");
        Entity<String> entity = Entity.entity("{}", MediaType.APPLICATION_JSON_TYPE);

        Response response = new ResteasyClientBuilder().build()
                .target(url)
                .request()
                .post(entity);

        Assert.assertEquals(200, response.getStatus());
        String body = response.readEntity(String.class);
        JSONAssert.assertEquals(getResourceContent("description/requests/empty.json"), body , true);
        response.close();

        server.stop();
    }


    @Test
    public void testSecondMetadataCall() throws IOException, JSONException, URISyntaxException {

        StoppableUndertowServer server = startServer();
        String url = testUrl("/metadata");
        Entity<String> entity = Entity.entity(getResourceContent("description/requests/with-parameters.json"), MediaType.APPLICATION_JSON_TYPE);

        Response response = new ResteasyClientBuilder().build()
                .target(url)
                .request()
                .post(entity);

        Assert.assertEquals(200, response.getStatus());
        String body = response.readEntity(String.class);
        JSONAssert.assertEquals(getResourceContent("description/requests/empty.json"), body , true);
        response.close();

        server.stop();
    }
}
