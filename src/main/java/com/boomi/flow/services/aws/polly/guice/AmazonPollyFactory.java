package com.boomi.flow.services.aws.polly.guice;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.AmazonPollyClientBuilder;
import com.boomi.flow.services.aws.polly.ApplicationConfiguration;

public class AmazonPollyFactory {
    public AmazonPolly create(ApplicationConfiguration configuration, AWSCredentials credentials) {

        return AmazonPollyClientBuilder.standard()
                .withRegion(configuration.getRegion())
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}
