package org.defalt.content.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FileUploadResponse {
    @JsonProperty
    private String object;
    @JsonProperty
    private String bucket;
}
