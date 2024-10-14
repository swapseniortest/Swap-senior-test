package com.swap.issues.recovery.client.github.repository_commits.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommitResponse {

    private String sha;
    private Commit commit;
    private Author author;
}
