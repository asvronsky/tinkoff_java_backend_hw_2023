package ru.asvronsky.scrapper.services;

import java.time.Duration;

public interface LinkUpdater {
    
    void update(Duration offset);

}
