package com.swap.issues.recovery.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.swap.issues.recovery.client.github.repository_commits.response.CommitResponse;
import com.swap.issues.recovery.client.github.repository_issues.response.IssueResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class IssueRecoveryServiceTest {

    public void teste() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        InputStream gitHubIssueResponseListInputStream = getClass().getClassLoader().getResourceAsStream("json/gitHubIssueResponseList.json");
        List<IssueResponse> issues = objectMapper.readValue(gitHubIssueResponseListInputStream, new TypeReference<List<IssueResponse>>() {});

        InputStream commitsInputStream = getClass().getClassLoader().getResourceAsStream("json/commitResponses.json");
        List<CommitResponse> commits = objectMapper.readValue(commitsInputStream, new TypeReference<List<CommitResponse>>() {});
    }
}
