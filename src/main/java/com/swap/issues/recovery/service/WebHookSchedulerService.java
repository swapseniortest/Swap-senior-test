package com.swap.issues.recovery.service;

import com.swap.issues.recovery.client.webhook_site.WebHookSiteClient;
import com.swap.issues.recovery.domain.dto.RepositorySnapshotDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebHookSchedulerService {

    @Value("${scheduler.webhook-delay:10}")
    private Long delay;

    private final WebHookSiteClient webHookSiteClient;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void scheduleWebhook(RepositorySnapshotDTO snapshot) {
        scheduler.schedule(() -> webHookSiteClient.sendWebhook(snapshot), delay, TimeUnit.SECONDS);
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}
