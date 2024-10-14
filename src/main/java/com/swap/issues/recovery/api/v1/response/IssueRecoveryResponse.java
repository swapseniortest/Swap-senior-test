package com.swap.issues.recovery.api.v1.response;

import com.swap.issues.recovery.domain.dto.ContributorDTO;
import com.swap.issues.recovery.domain.dto.IssueDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueRecoveryResponse {

    private String user;
    private String repository;
    private List<IssueDTO> issues;
    private List<ContributorDTO> contributors;

}
