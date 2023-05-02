package ru.asvronsky.scrapper.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.asvronsky.scrapper.configuration.ApplicationConfig;
import ru.asvronsky.scrapper.services.LinkUpdater;

@Service
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    
    private final LinkUpdater linkUpdater;
    private final ApplicationConfig config;
    
    @Scheduled(fixedDelayString = "#{schedulerIntervalMs}")
    public void update() {
        linkUpdater.update(config.scheduler().interval());
    }
}
