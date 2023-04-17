package ru.asvronsky.scrapper.repository;

import java.time.Duration;
import java.util.List;

import ru.asvronsky.scrapper.model.Link;

public interface LinkDao {
    
    public List<Link> findOutdated(Duration offset);
    public List<String> update(Link link);

}
