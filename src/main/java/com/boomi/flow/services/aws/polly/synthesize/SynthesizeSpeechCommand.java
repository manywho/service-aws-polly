package com.boomi.flow.services.aws.polly.synthesize;

import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.services.polly.model.VoiceId;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.boomi.flow.services.aws.polly.ApplicationConfiguration;
import com.boomi.flow.services.aws.polly.synthesize.SynthesizeSpeechAction.Input;
import com.boomi.flow.services.aws.polly.synthesize.SynthesizeSpeechAction.Output;
import com.manywho.sdk.api.InvokeType;
import com.manywho.sdk.api.run.elements.config.ServiceRequest;
import com.manywho.sdk.services.actions.ActionCommand;
import com.manywho.sdk.services.actions.ActionResponse;

import javax.inject.Inject;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

public class SynthesizeSpeechCommand implements ActionCommand<ApplicationConfiguration, SynthesizeSpeechAction, Input, Output> {
    private final AmazonPolly polly;
    private final AmazonS3 s3;

    @Inject
    public SynthesizeSpeechCommand(AmazonPolly polly, AmazonS3 s3) {
        this.polly = polly;
        this.s3 = s3;
    }

    @Override
    public ActionResponse<Output> execute(ApplicationConfiguration configuration, ServiceRequest request, Input input) {
        // First we generate the synthesized speech
        SynthesizeSpeechResult speechResult = polly.synthesizeSpeech(
                new SynthesizeSpeechRequest()
                        .withOutputFormat(OutputFormat.Mp3)
                        .withText(input.getText())
                        .withVoiceId(VoiceId.Amy)
        );

        // Now we want to upload it temporarily to S3
        String key = UUID.randomUUID().toString();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(speechResult.getContentType());

        s3.putObject(System.getenv("AWS_S3_BUCKET"), key, speechResult.getAudioStream(), objectMetadata);

        // Now we want to generate a URL to view the generated audio for 1 hour
        Date expiration = new Date();
        long msec = expiration.getTime();
        msec += 1000 * 60 * 60;
        expiration.setTime(msec);

        URL audioFileUrl = s3.generatePresignedUrl(
                System.getenv("AWS_S3_BUCKET"),
                key,
                expiration
        );

        return new ActionResponse<>(new Output(audioFileUrl.toString()), InvokeType.Forward);
    }
}
