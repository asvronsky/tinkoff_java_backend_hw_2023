package ru.asvronsky.scrapper.repository.jpa;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.mapstruct.factory.Mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import ru.asvronsky.scrapper.model.Link;
import ru.asvronsky.scrapper.model.jpa.LinkEntity;
import ru.asvronsky.scrapper.model.jpa.LinkEntityMapper;
import ru.asvronsky.scrapper.repository.LinkDao;

@RequiredArgsConstructor
public class JpaLinkDao implements LinkDao {

    private final JpaLinkEntityRepository entityRepository;

    private final LinkEntityMapper mapper = Mappers.getMapper(LinkEntityMapper.class);

    @Override
    public List<Link> findOutdated(Duration offset) {
        return entityRepository.findAllByUpdatedAtLessThan(OffsetDateTime.now().minus(offset))
            .stream()
            .map(mapper::toLink)
            .toList();
    }

    @Override
    public List<String> update(Link link) {
        LinkEntity entity = entityRepository.findById(link.getId())
            .orElseThrow(() -> new IllegalStateException("Link %s not found".formatted(link.toString())));

        entityRepository.saveAndFlush(mapper.toLinkEntity(link));
        
        try {
            return getJsonDifference(link.getWebsiteData(), entity.getWebsiteData());
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(
                "Failed when getting difference of links %s %s".formatted(link.getWebsiteData(), entity.getWebsiteData())
            );
        }
    }

    @Override
    public Optional<Link> remove(Link link) {
        Optional<LinkEntity> entity = entityRepository.deleteByUrl(link.getUrl());
        entityRepository.flush();

        if (entity.isPresent()) {
            return Optional.of(mapper.toLink(entity.get()));
        } else {
            return Optional.empty();
        }  
    }

    private List<String> getJsonDifference(String newJsonString, String oldJsonString) 
        throws JsonMappingException, JsonProcessingException {
        ObjectMapper jsonMapper = new ObjectMapper();
        TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {};

        Map<String, String> newMap = jsonMapper.readValue(newJsonString, typeRef);
        Map<String, String> oldMap = jsonMapper.readValue(oldJsonString, typeRef);

        Map<String, String> difference = new HashMap<>();
        difference.putAll(newMap);
        difference.entrySet().removeAll(oldMap.entrySet());
        
        return difference.keySet().stream().toList();
    }

}
