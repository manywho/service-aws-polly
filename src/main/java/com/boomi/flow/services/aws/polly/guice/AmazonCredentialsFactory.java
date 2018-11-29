package com.boomi.flow.services.aws.polly.guice;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.boomi.flow.services.aws.polly.ApplicationConfiguration;

public class AmazonCredentialsFactory {

    public AWSCredentials create(ApplicationConfiguration configuration) {

        return new BasicAWSCredentials(
                configuration.getAccessKey(),
                configuration.getSecretKey()
        );
    }
}