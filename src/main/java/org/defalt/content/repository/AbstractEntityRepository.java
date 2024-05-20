package org.defalt.content.repository;

import org.defalt.content.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;
import java.util.Optional;

@NoRepositoryBean
public interface AbstractEntityRepository<E extends AbstractEntity> extends JpaRepository<E, Long> {
    Optional<E> findByUid(String Uid);
    Collection<E> findByUidIn(Collection<String> uidList);
    void deleteByUid(String uid);
}
