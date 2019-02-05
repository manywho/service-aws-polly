package com.boomi.flow.services.aws.polly;

import com.manywho.sdk.api.ContentType;
import com.manywho.sdk.services.configuration.Configuration;

import java.util.Objects;

public class ApplicationConfiguration implements Configuration {

    public ApplicationConfiguration() {
    }

    public ApplicationConfiguration(String bucketName, String accessKey, String secretKey, String region) {
        this.bucketName = bucketName;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.region = region;
    }

    @Configuration.Setting(name = "Bucket Name", contentType = ContentType.String)
    private String bucketName;

    @Configuration.Setting(name = "Access Key", contentType = ContentType.String)
    private String accessKey;

    @Configuration.Setting(name = "Secret Key", contentType = ContentType.Password)
    private String secretKey;

    @Configuration.Setting(name = "Region", contentType = ContentType.String)
    private String region;

    public String getBucketName() {
        return bucketName;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getRegion() {
        return region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationConfiguration that = (ApplicationConfiguration) o;
        return Objects.equals(bucketName, that.bucketName) &&
                Objects.equals(accessKey, that.accessKey) &&
                Objects.equals(secretKey, that.secretKey) &&
                Objects.equals(region, that.region);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bucketName, accessKey, secretKey, region);
    }
}
