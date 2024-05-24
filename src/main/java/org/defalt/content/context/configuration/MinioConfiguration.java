package org.defalt.content.context.configuration;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfiguration {
    @Bean
    public MinioClient client(@Value("hosts.minio") String host) {
        return MinioClient.builder()
                .endpoint(host)
                .credentials("minioadmin", "minioadmin")
                .build();
    }
}
