package com.swap.issues.recovery.mapper;

import com.swap.issues.recovery.client.github.repository_commits.response.CommitResponse;
import com.swap.issues.recovery.client.github.repository_issues.response.IssueResponse;
import com.swap.issues.recovery.client.github.repository_issues.response.Label;
import com.swap.issues.recovery.domain.dto.ContributorDTO;
import com.swap.issues.recovery.domain.dto.IssueDTO;
import com.swap.issues.recovery.domain.dto.RepositorySnapshotDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RepositorySnapshotMapper {

    public static RepositorySnapshotDTO mapToRepositorySnapshotDTO(
            String owner,
            String repo,
            List<IssueResponse> issues,
            List<CommitResponse> contributors) {

        RepositorySnapshotDTO response = new RepositorySnapshotDTO();
        response.setUser(owner);
        response.setRepository(repo);
        if (issues != null && contributors != null) {
            var issueDetails = toIssueDTO(issues);
            var contributorsDetails = mapContributors(contributors);
            response.setIssues(issueDetails);
            response.setContributors(contributorsDetails);

        }

        return response;
    }

    private static List<IssueDTO> toIssueDTO(List<IssueResponse> issues) {
        return issues.stream().map(issue -> {

            var title = issue.getTitle();
            var author = issue.getUser() != null ? issue.getUser().getLogin() : "unknown";
            var labels = issue.getLabels().stream().map(Label::getName).collect(Collectors.toList());

            return new IssueDTO(title, author, labels);
        }).collect(Collectors.toList());
    }

    public static List<ContributorDTO> mapContributors(List<CommitResponse> commits) {
        Map<String, ContributorDTO> contributorMap = new HashMap<>();

        commits.forEach(commit -> {
            String name = commit.getCommit().getAuthor().getName();
            String login = commit.getAuthor().getLogin();
            contributorMap.putIfAbsent(login, new ContributorDTO(name, login, 0));
            ContributorDTO contributor = contributorMap.get(login);
            contributor.setQtdCommits(contributor.getQtdCommits() + 1);  // Incrementa o número de commits
        });
        return contributorMap.values().stream().collect(Collectors.toList());
    }
}
