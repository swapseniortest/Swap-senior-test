package com.swap.issues.recovery.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swap.issues.recovery.api.v1.response.IssueRecoveryResponse;
import com.swap.issues.recovery.api.v1.response.TemporaryResponse;
import com.swap.issues.recovery.client.github.repository_issues.response.IssueResponse;
import com.swap.issues.recovery.service.IssueRecoveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/github")
public class GitHubSnapshotController implements GitHubSnapshotApi {

    private final ObjectMapper objectMapper;
    private final IssueRecoveryService issueRecoveryService;

    @Override
    @PostMapping("/snapshot-issues")
    public IssueRecoveryResponse createSnapShotGitHubIssues(@RequestParam final String userName,
                                                            @RequestParam final String repositoryName) {
        var result = issueRecoveryService.createSnapShotGitHubIssues(userName, repositoryName);
        return objectMapper.convertValue(result, IssueRecoveryResponse.class);
    }

}

