package ru.asvronsky.scrapper.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LinkUpdaterScheduler {
    
    @Scheduled(fixedDelayString = "#{schedulerIntervalMs}")
    public void update() {
        log.debug("update");
    }
}
