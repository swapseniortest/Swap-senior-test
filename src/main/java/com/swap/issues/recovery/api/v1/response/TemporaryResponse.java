package com.swap.issues.recovery.api.v1.response;

import com.swap.issues.recovery.client.github.repository_commits.response.CommitResponse;
import com.swap.issues.recovery.client.github.repository_issues.response.IssueResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemporaryResponse {

    private List<IssueResponse> issueResponseList;
    private List<CommitResponse> commitResponses;
}
