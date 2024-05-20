package org.defalt.content.repository;

import org.defalt.content.entity.MediaFrame;
import org.defalt.content.entity.User;

import java.util.Optional;

public interface MediaFrameRepository extends AbstractEntityRepository<MediaFrame> {
    Optional<MediaFrame> getByUserAndName(User user, String name);
}
