package com.boomi.flow.services.aws.polly;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.s3.AmazonS3;
import com.boomi.flow.services.aws.polly.guice.AmazonCredentialsProvider;
import com.boomi.flow.services.aws.polly.guice.AmazonPollyProvider;
import com.boomi.flow.services.aws.polly.guice.AmazonS3Provider;
import com.google.inject.AbstractModule;

public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(AWSCredentials.class).toProvider(AmazonCredentialsProvider.class);
        bind(AmazonPolly.class).toProvider(AmazonPollyProvider.class);
        bind(AmazonS3.class).toProvider(AmazonS3Provider.class);
    }
}