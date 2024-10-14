package com.swap.issues.recovery.domain.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContributorDTO {

    private String name;
    private String user;
    private Integer qtdCommits;

}
