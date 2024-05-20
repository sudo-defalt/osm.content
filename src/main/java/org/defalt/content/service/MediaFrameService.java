package org.defalt.content.service;

import io.minio.GetObjectResponse;
import io.minio.ObjectWriteResponse;
import org.defalt.content.context.CurrentApplicationContext;
import org.defalt.content.context.auth.UserSecurityContext;
import org.defalt.content.entity.MediaFrame;
import org.defalt.content.entity.User;
import org.defalt.content.model.MediaFrameModel;
import org.defalt.content.repository.MediaFrameRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

@Service
public class MediaFrameService {
    private final MediaFrameRepository repository;
    private final FileService fileService;

    public MediaFrameService(MediaFrameRepository repository, FileService fileService) {
        this.repository = repository;
        this.fileService = fileService;
    }

    public static MediaFrameService getInstance() {
        return CurrentApplicationContext.getBean(MediaFrameService.class);
    }

    @Transactional
    public MediaFrameModel save(InputStream stream, long fileSize) {
        User user = UserSecurityContext.getCurrentUser().getUser();
        ObjectWriteResponse writeResponse = fileService.save(stream, user.getUsername(), fileSize);
        MediaFrame mediaFrame = new MediaFrame();
        mediaFrame.setUser(user);
        mediaFrame.setBucket(writeResponse.bucket());
        mediaFrame.setName(writeResponse.object());
        repository.save(mediaFrame);
        return new MediaFrameModel(mediaFrame, stream);
    }

    public MediaFrameModel get(String extuid) {
        MediaFrame mediaFrame = repository.findByUid(extuid)
                .orElseThrow();
        GetObjectResponse object = fileService.get(mediaFrame.getName(), mediaFrame.getBucket())
                .orElseThrow();

        return new MediaFrameModel(mediaFrame, object);
    }

}
