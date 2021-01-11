package com.flow.services.services;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LakeService {

    @Value("${lake.s3BucketName:mlops-lake}")
    private String LAKE_S3_BUCKET_NAME;

    private final AmazonS3 s3;

    public LakeService() {
        this.s3 = AmazonS3ClientBuilder
                .standard()
                .withClientConfiguration(new ClientConfiguration().withProtocol(Protocol.HTTP))
                .withRegion(Regions.US_EAST_1).build();
    }

    public Set<String> listFiles() {
        ObjectListing objectListing = this.s3.listObjects(LAKE_S3_BUCKET_NAME, "files");
        return objectListing.getObjectSummaries()
                .stream()
                .map(S3ObjectSummary::getKey)
                .filter(s -> s.split("/").length > 1)
                .map(s -> s.split("/")[1])
                .collect(Collectors.toSet());
    }

    public Set<String> listTables() {
        ObjectListing objectListing = this.s3.listObjects(LAKE_S3_BUCKET_NAME, "tables");
        return objectListing.getObjectSummaries()
                .stream()
                .map(S3ObjectSummary::getKey)
                .filter(s -> s.split("/").length > 1)
                .map(s -> s.split("/")[1])
                .collect(Collectors.toSet());
    }

    public Set<String> listModels() {
        ObjectListing objectListing = this.s3.listObjects(LAKE_S3_BUCKET_NAME, "models");
        return objectListing
                .getObjectSummaries()
                .stream()
                .map(S3ObjectSummary::getKey)
                .filter(s -> s.split("/").length > 1)
                .map(s -> s.split("/")[1])
                .collect(Collectors.toSet());
    }
}
