package com.swap.issues.recovery.domain.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RepositorySnapshotDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String user;
    private String repository;
    private List<IssueDTO> issues;
    private List<ContributorDTO> contributors;

}
