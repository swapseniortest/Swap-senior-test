package com.swap.issues.recovery.service;

import com.swap.issues.recovery.client.github.GitHubClient;
import com.swap.issues.recovery.client.github.repository_commits.response.CommitResponse;
import com.swap.issues.recovery.client.github.repository_issues.response.IssueResponse;
import com.swap.issues.recovery.domain.dto.RepositorySnapshotDTO;
import com.swap.issues.recovery.mapper.RepositorySnapshotMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class IssueRecoveryService {

    private final GitHubClient gitHubClient;
    private final WebHookSchedulerService webHookSchedulerService;

    @Async
    public CompletableFuture<List<IssueResponse>> getIssuesAsync(final String userName, final String repositoryName) {
        return CompletableFuture.completedFuture(gitHubClient.getIssues(userName, repositoryName));
    }

    @Async
    public CompletableFuture<List<CommitResponse>> getCommitsAsync(final String userName, final String repositoryName) {
        return CompletableFuture.completedFuture(gitHubClient.getContributorsFromCommits(userName, repositoryName));
    }

    public RepositorySnapshotDTO createSnapShotGitHubIssues(final String userName, final String repositoryName) {
        CompletableFuture<List<IssueResponse>> issuesFuture = getIssuesAsync(userName, repositoryName);
        CompletableFuture<List<CommitResponse>> commitsFuture = getCommitsAsync(userName, repositoryName);

        CompletableFuture<RepositorySnapshotDTO> repositorySnapshotFuture = issuesFuture.thenCombine(commitsFuture,
                (issues, commits) -> RepositorySnapshotMapper.mapToRepositorySnapshotDTO(userName, repositoryName, issues, commits));

        repositorySnapshotFuture.thenAccept(webHookSchedulerService::scheduleWebhook);

        return repositorySnapshotFuture.join();
    }
}
