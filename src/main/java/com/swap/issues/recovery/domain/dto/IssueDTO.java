package com.swap.issues.recovery.domain.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssueDTO {

    private String title;
    private String author;
    private List<String> labels;

}
