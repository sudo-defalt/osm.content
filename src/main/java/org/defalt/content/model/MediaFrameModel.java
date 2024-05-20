package org.defalt.content.model;

import org.defalt.content.entity.MediaFrame;

import java.io.InputStream;

public record MediaFrameModel(MediaFrame mediaFrame, InputStream stream) {

    public String getName() {
        return mediaFrame.getName();
    }

    public String getBucket() {
        return mediaFrame.getBucket();
    }

    public long getSize() {
        return mediaFrame.getSize();
    }

    public InputStream getStream() {
        return stream;
    }
}
