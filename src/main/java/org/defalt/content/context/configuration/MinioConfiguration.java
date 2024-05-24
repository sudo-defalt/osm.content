package org.defalt.content.context.configuration;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfiguration {
    @Bean
    public MinioClient client() {
        return MinioClient.builder()
                .endpoint("http://osm.minio:9000")
                .credentials("minioadmin", "minioadmin")
                .build();
    }
}
