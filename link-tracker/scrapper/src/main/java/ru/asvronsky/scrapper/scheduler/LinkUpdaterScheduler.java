package ru.asvronsky.scrapper.scheduler;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.asvronsky.scrapper.services.LinkUpdater;

@Service
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    
    private final LinkUpdater linkUpdater;
    
    @Scheduled(fixedDelayString = "#{schedulerIntervalMs}")
    @Value("#{schedulerIntervalMs}")
    public void update(long offset) {
        linkUpdater.update(Duration.ofMillis(offset));
    }
}
