package com.swap.issues.recovery.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swap.issues.recovery.api.v1.response.IssueRecoveryResponse;
import com.swap.issues.recovery.api.v1.response.ScheduleResponse;
import com.swap.issues.recovery.service.IssueRecoveryService;
import com.swap.issues.recovery.service.JobConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/github")
public class GitHubSnapshotController implements GitHubSnapshotApi {

    private final ObjectMapper objectMapper;
    private final IssueRecoveryService issueRecoveryService;
    private final JobConsultationService jobConsultationService;

    @Override
    @PostMapping("/snapshot-issues")
    public IssueRecoveryResponse createSnapShotGitHubIssues(@RequestParam final String userName,
                                                            @RequestParam final String repositoryName) {
        var result = issueRecoveryService.createSnapShotGitHubIssues(userName, repositoryName);
        return objectMapper.convertValue(result, IssueRecoveryResponse.class);
    }

    @Override
    @GetMapping("/existing-jobs")
    public List<ScheduleResponse> listJobs() {
        return jobConsultationService.listScheduledJobs();
    }

}

