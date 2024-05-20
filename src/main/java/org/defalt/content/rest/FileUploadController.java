package org.defalt.content.rest;

import org.defalt.content.rest.dto.FileUploadResponse;
import org.defalt.content.model.MediaFrameModel;
import org.defalt.content.service.MediaFrameService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("upload")
public class FileUploadController {
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            MediaFrameModel model = MediaFrameService.getInstance().save(inputStream, file.getSize());
            return ResponseEntity.ok(FileUploadResponse.builder()
                    .object(model.getName()).bucket(model.getBucket())
                    .build());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
