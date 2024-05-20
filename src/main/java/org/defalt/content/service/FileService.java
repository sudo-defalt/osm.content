package org.defalt.content.service;

import io.minio.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import org.apache.commons.lang3.RandomStringUtils;
import org.defalt.content.context.CurrentApplicationContext;
import org.defalt.content.context.auth.UserSecurityContext;
import org.defalt.content.model.MediaAccessToken;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FileService {
    protected final MinioClient client;
    public FileService(MinioClient client) {
        this.client = client;
    }

    public static FileService getInstance() {
        return CurrentApplicationContext.getBean(FileService.class);
    }


    public ObjectWriteResponse save(InputStream stream, String bucket, long fileSize) {
        if (!userBucketExists())
            createUserBucket();
        try {
            return client.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(RandomStringUtils.randomAlphanumeric(32))
                    .stream(stream, fileSize, -1)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public GetObjectResponse get(String name) {
        return get(name, UserSecurityContext.getCurrentUser().getUsername()).orElseThrow();
    }

    public Optional<GetObjectResponse> get(String name, String bucket) {
        try {
            return Optional.ofNullable(client.getObject(GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(name)
                    .build()));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public boolean userBucketExists() {
        try {
            return client.bucketExists(BucketExistsArgs.builder().bucket(UserSecurityContext.getCurrentUser().getUsername()).build());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean createUserBucket() {
        try {
            client.makeBucket(MakeBucketArgs.builder().bucket(UserSecurityContext.getCurrentUser().getUsername()).build());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<GetObjectResponse> get(MediaAccessToken accessToken) {
        if (accessToken.hasAccess(UserSecurityContext.getCurrentUser().getUser()))
            return get(accessToken.getFile(), accessToken.getOwner());
        else
            return Optional.empty();
    }

    public void cleanUp() {
        try {
            List<Bucket> buckets = client.listBuckets();
            for (Bucket bucket : buckets) {
                Iterable<Result<Item>> objects = client.listObjects(ListObjectsArgs.builder()
                        .bucket(bucket.name())
                        .build());
                List<String> objectNames = StreamSupport.stream(objects.spliterator(), false)
                        .map(itemResult -> {
                            try {
                                return itemResult.get().objectName();
                            } catch (Exception e) {
                                return null;
                            }
                        }).collect(Collectors.toList());
                client.removeObject(RemoveObjectArgs.builder().build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
