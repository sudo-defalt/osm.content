package org.defalt.content.repository;

import org.defalt.content.entity.User;

import java.util.Optional;

public interface UserRepository extends AbstractEntityRepository<User> {
    Optional<User> getByUsername(String username);
}
