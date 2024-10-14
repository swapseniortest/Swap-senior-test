package com.swap.issues.recovery.client.github.repository_commits.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commit {

    private Author author;
    private String message;
}
