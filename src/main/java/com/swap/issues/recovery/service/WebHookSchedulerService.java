package com.swap.issues.recovery.service;

import com.swap.issues.recovery.domain.dto.RepositorySnapshotDTO;
import com.swap.issues.recovery.jobs.WebhookJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebHookSchedulerService {

    @Value("${scheduler.webhook-delay}")
    private int delay;

    private final Scheduler scheduler;

    public void scheduleWebhook(RepositorySnapshotDTO snapshot) {
        try {
            var jobDetail = buildJobDetail(snapshot);
            var trigger = buildTrigger(jobDetail);

            scheduler.scheduleJob(jobDetail, trigger);

            log.info("Webhook agendado para o snapshot do repositório: {} de {}", snapshot.getRepository(), snapshot.getUser());
        } catch (SchedulerException e) {
            log.error("Erro ao agendar o envio do webhook", e);
        }
    }

    private JobDetail buildJobDetail(RepositorySnapshotDTO snapshot) {
        var jobDataMap = new JobDataMap();
        jobDataMap.put("snapshot", snapshot);

        var jobId = "job_" + UUID.randomUUID();

        return JobBuilder.newJob(WebhookJob.class)
                .withIdentity(jobId, "webhookGroup")
                .usingJobData(jobDataMap)
                .build();
    }

    private Trigger buildTrigger(JobDetail jobDetail) {
        var triggerId = "trigger_" + UUID.randomUUID();

        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(triggerId, "webhookGroup")
                .startAt(DateBuilder.futureDate(delay, DateBuilder.IntervalUnit.SECOND))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }
}
