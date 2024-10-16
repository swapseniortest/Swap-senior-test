package com.swap.issues.recovery.jobs;

import com.swap.issues.recovery.client.webhook_site.WebHookSiteClient;
import com.swap.issues.recovery.domain.dto.RepositorySnapshotDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebhookJob implements Job {

    private final WebHookSiteClient webHookSiteClient;

    @Override
    public void execute(JobExecutionContext context) {
        var jobDataMap = context.getJobDetail().getJobDataMap();
        var snapshot = (RepositorySnapshotDTO) jobDataMap.get("snapshot");

        webHookSiteClient.sendWebhook(snapshot);

        log.info("Enviado snapshot do repositório: {}, e usuário {} para o webhook", snapshot.getRepository(), snapshot.getUser());
    }
}
