package com.swap.issues.recovery.domain.dto;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContributorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String user;
    private Integer qtdCommits;

}
