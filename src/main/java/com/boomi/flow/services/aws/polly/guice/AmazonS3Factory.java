package com.boomi.flow.services.aws.polly.guice;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.boomi.flow.services.aws.polly.ApplicationConfiguration;

public class AmazonS3Factory {
    public AmazonS3 create(ApplicationConfiguration configuration, AWSCredentials credentials) {

        return AmazonS3ClientBuilder.standard()
                .withRegion(configuration.getRegion())
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}
