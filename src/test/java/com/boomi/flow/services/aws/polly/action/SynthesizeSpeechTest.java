package com.boomi.flow.services.aws.polly.action;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.services.polly.model.VoiceId;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.boomi.flow.services.aws.polly.ApplicationConfiguration;
import com.boomi.flow.services.aws.polly.BaseTest;
import com.boomi.flow.services.aws.polly.guice.AmazonCredentialsFactory;
import com.boomi.flow.services.aws.polly.guice.AmazonPollyFactory;
import com.boomi.flow.services.aws.polly.guice.AmazonS3Factory;
import com.boomi.flow.services.aws.polly.synthesize.SynthesizeSpeechCommand;
import com.google.inject.AbstractModule;
import com.manywho.sdk.services.servers.EmbeddedServer;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.json.JSONException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class SynthesizeSpeechTest extends BaseTest {
    private static EmbeddedServer server;

    @Mock
    protected AmazonCredentialsFactory amazonCredentialsFactory;

    @Mock
    protected AmazonS3Factory amazonS3Factory;

    @Mock
    protected AmazonPollyFactory amazonPollyFactory;

    @Mock
    protected AWSCredentials awsCredentials;

    @Mock
    protected AmazonPolly amazonPolly;

    @Mock
    protected AmazonS3 amazonS3;

    @Mock
    protected SynthesizeSpeechResult synthesizeSpeechResult;

    @InjectMocks
    protected SynthesizeSpeechCommand synthesizeSpeechCommand;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        server = startServer();
    }

    @After
    public void cleanUp() {
        server.stop();
    }

    @Test
    public void testSynthesizeSpeechTest() throws IOException, JSONException {
        // the same configuration as sent in the request
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration("bucket-name-test",
                "access-key-test", "secret-key-test", "eu-west-2");

        String url = createTestUrl("/actions/synthesize/speech");
        Entity<String> entity = Entity.entity(getResourceContent("/synthesize/synthesize-request.json"), MediaType.APPLICATION_JSON_TYPE);

        when(amazonCredentialsFactory.create(eq(applicationConfiguration)))
                .thenReturn(awsCredentials);

        when(amazonPollyFactory.create(eq(applicationConfiguration), any(AWSCredentials.class)))
                .thenReturn(amazonPolly);

        when(amazonS3Factory.create(eq(applicationConfiguration), any(AWSCredentials.class)))
                .thenReturn(amazonS3);

        SynthesizeSpeechRequest synthesizeSpeechRequest = new SynthesizeSpeechRequest()
                .withOutputFormat(OutputFormat.Mp3)
                .withText("this is a test text")
                .withVoiceId(VoiceId.Amy);

        when(amazonPolly.synthesizeSpeech(eq(synthesizeSpeechRequest)))
                .thenReturn(synthesizeSpeechResult);

        when(amazonS3.generatePresignedUrl(eq("bucket-name-test"), any(String.class), any(Date.class)))
                .thenReturn(new URL("http://audio.test"));

        when(synthesizeSpeechResult.getAudioStream())
                .thenReturn(mock(InputStream.class));

        Response response = new ResteasyClientBuilder().build()
                .target(url)
                .request()
                .post(entity);

        verify(amazonS3, atLeastOnce()).putObject(eq("bucket-name-test"), any(String.class), any(InputStream.class), any(ObjectMetadata.class));

        Assert.assertEquals(200, response.getStatus());
        assertExpectedBody("/synthesize/synthesize-response.json", response);
        response.close();
    }

    protected AbstractModule injectModule() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(SynthesizeSpeechCommand.class).toInstance(synthesizeSpeechCommand);
            }
        };
    }
}
