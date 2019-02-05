package com.boomi.flow.services.aws.polly.description;

import com.boomi.flow.services.aws.polly.BaseTest;
import com.boomi.flow.services.aws.polly.StoppableUndertowServer;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class DescriptionTest extends BaseTest {
    @Test
    public void testFirsMetadataCall() {

        StoppableUndertowServer server = startServer();
        String url = testUrl("/metadata");
        Entity<String> entity = Entity.entity("{}", MediaType.APPLICATION_JSON_TYPE);

        Response response = new ResteasyClientBuilder().build()
                .target(url)
                .request()
                .post(entity);

        Assert.assertEquals(200, response.getStatus());
        assertExpectedBody("descriptions/without-parameters-response.json", response);
        response.close();

        server.stop();
    }


    @Test
    public void testSecondMetadataCall() {

        StoppableUndertowServer server = startServer();
        String url = testUrl("/metadata");
        Entity<String> entity = Entity.entity(getResourceContent("descriptions/with-parameters-request.json"), MediaType.APPLICATION_JSON_TYPE);

        Response response = new ResteasyClientBuilder().build()
                .target(url)
                .request()
                .post(entity);

        Assert.assertEquals(200, response.getStatus());
        assertExpectedBody("descriptions/with-parameters-response.json", response);

        response.close();
        server.stop();
    }
}
