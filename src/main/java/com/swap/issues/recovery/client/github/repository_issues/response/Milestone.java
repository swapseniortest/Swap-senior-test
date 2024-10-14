package com.swap.issues.recovery.client.github.repository_issues.response;

import lombok.Data;

@Data
public class Milestone {
    private Long id;
    private String nodeId;
    private String url;
    private String htmlUrl;
    private String labelsUrl;
    private Integer number;
    private String state;
    private String title;
    private String description;
    private User creator;
    private Integer openIssues;
    private Integer closedIssues;
    private String createdAt;
    private String updatedAt;
    private String closedAt;
    private String dueOn;

    // Getters e Setters
}
