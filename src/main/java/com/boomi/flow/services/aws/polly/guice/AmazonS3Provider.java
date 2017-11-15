package com.boomi.flow.services.aws.polly.guice;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.google.inject.Provider;

import javax.inject.Inject;

public class AmazonS3Provider implements Provider<AmazonS3> {
    private final AWSCredentials credentials;

    @Inject
    public AmazonS3Provider(AWSCredentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public AmazonS3 get() {
        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}
