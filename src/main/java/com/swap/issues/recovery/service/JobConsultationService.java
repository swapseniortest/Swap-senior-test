package com.swap.issues.recovery.service;

import com.swap.issues.recovery.api.v1.response.ScheduleResponse;
import com.swap.issues.recovery.api.v1.response.TriggerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobConsultationService {

    private final Scheduler scheduler;

    public List<ScheduleResponse> listScheduledJobs() {
        List<ScheduleResponse> jobsScheduled = new ArrayList<>();
        try {
            List<String> jobGroupNames = scheduler.getJobGroupNames();

            for (String groupName : jobGroupNames) {
                Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName));
                for (JobKey jobKey : jobKeys) {
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);

                    var scheduleResponse = ScheduleResponse.builder()
                            .job(jobDetail.getKey().getName())
                            .description(jobDetail.getDescription())
                            .build();

                    List<TriggerResponse> triggersResponse = new ArrayList<>();
                    List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                    for (Trigger trigger : triggers) {
                        var triggerResponse = TriggerResponse.builder()
                                .trigger(trigger.getKey().getName())
                                .nextExecution(trigger.getNextFireTime())
                                .build();
                        triggersResponse.add(triggerResponse);
                    }

                    scheduleResponse.setTriggers(triggersResponse);
                    jobsScheduled.add(scheduleResponse);
                }
            }
        } catch (SchedulerException e) {
            log.error("Erro ao consultar jobs agendados", e);
        }
        return jobsScheduled;
    }
}