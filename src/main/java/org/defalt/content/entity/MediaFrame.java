package org.defalt.content.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity(name = MediaFrame.ENTITY_NAME)
@Table(name = MediaFrame.TABLE_NAME)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class MediaFrame extends AbstractEntity {
    public static final String ENTITY_NAME = "MediaFrame";
    public static final String TABLE_NAME = "media_frames";

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    private String bucket;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private long size;
}
