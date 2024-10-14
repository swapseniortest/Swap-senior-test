package com.swap.issues.recovery.domain.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RepositorySnapshotDTO {

    private String user;
    private String repository;
    private List<IssueDTO> issues;
    private List<ContributorDTO> contributors;

}
