package org.defalt.content.rest;

import org.defalt.content.model.MediaAccessToken;
import org.defalt.content.service.FileService;
import org.defalt.content.util.CipherUtils;
import org.defalt.content.util.CipheringProcessException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("download")
public class FileDownloadController {

    @GetMapping(produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<Resource> download(@RequestHeader("accessToken") String token) throws CipheringProcessException {
        MediaAccessToken accessToken = CipherUtils.getInstance().decryptAccess(token);
        try (InputStream stream = FileService.getInstance().get(accessToken).orElseThrow()) {
            if (stream == null)
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok().body(new ByteArrayResource(stream.readAllBytes()));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
