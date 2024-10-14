package com.swap.issues.recovery.client.github.repository_issues.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueResponse {

    private Long id;

    private String url;

    @JsonProperty("node_id")
    private String nodeId;

    @JsonProperty("repository_url")
    private String repositoryUrl;

    @JsonProperty("labels_url")
    private String labelsUrl;

    @JsonProperty("comments_url")
    private String commentsUrl;

    @JsonProperty("events_url")
    private String eventsUrl;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("active_lock_reason")
    private String activeLockReason;

    @JsonProperty("pull_request")
    private PullRequest pullRequest;

    @JsonProperty("closed_by")
    private User closedBy;

    @JsonProperty("author_association")
    private String authorAssociation;

    @JsonProperty("timeline_url")
    private String timelineUrl;

    @JsonProperty("state_reason")
    private String stateReason;

    @JsonProperty("closed_at")
    private LocalDateTime closedAt;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    private Integer number;
    private String state;
    private String title;
    private String body;
    private User user;
    private List<Label> labels;
    private User assignee;
    private List<User> assignees;
    private Milestone milestone;
    private Boolean locked;
    private Integer comments;
}

