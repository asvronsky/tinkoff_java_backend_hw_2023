package ru.asvronsky.scrapper.repository.jpa;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.asvronsky.scrapper.model.jpa.LinkEntity;

public interface JpaLinkEntityRepository extends JpaRepository<LinkEntity, Long>  {
    List<LinkEntity> findAllByUpdatedAtLessThan(OffsetDateTime offset);
    Optional<LinkEntity> findByUrl(String url);
    Optional<LinkEntity> deleteByUrl(String url);
}
