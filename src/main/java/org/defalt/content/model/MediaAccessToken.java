package org.defalt.content.model;

import lombok.Getter;
import org.defalt.content.entity.User;

import java.time.Instant;
import java.util.Date;

@Getter
public class MediaAccessToken {
    private final String owner;
    private final String user;
    private final String file;
    private final Date expirationDate;

    public MediaAccessToken(String accessTokenLiteral) {
        String[] tokens = accessTokenLiteral.split(";");
        this.owner = tokens[0];
        this.file = tokens[1];
        this.user = tokens[2];
        this.expirationDate = Date.from(Instant.ofEpochMilli(Long.parseLong(tokens[3])));
    }

    public boolean hasAccess(User user) {
        return expirationDate.after(new Date()) && user.getUsername().equals(this.user);
    }

}
