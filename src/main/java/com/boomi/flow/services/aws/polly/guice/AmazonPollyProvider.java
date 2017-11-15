package com.boomi.flow.services.aws.polly.guice;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.AmazonPollyClientBuilder;
import com.google.inject.Provider;

import javax.inject.Inject;

public class AmazonPollyProvider implements Provider<AmazonPolly> {
    private final AWSCredentials credentials;

    @Inject
    public AmazonPollyProvider(AWSCredentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public AmazonPolly get() {
        return AmazonPollyClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}
