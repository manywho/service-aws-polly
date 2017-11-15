package com.boomi.flow.services.aws.polly.guice;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.google.inject.Provider;

public class AmazonCredentialsProvider implements Provider<AWSCredentials> {
    @Override
    public AWSCredentials get() {
        return new BasicAWSCredentials(
                System.getenv("AWS_ACCESS_KEY"),
                System.getenv("AWS_SECRET_KEY")
        );
    }
}