package com.swap.issues.recovery.domain.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssueDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;
    private String author;
    private List<String> labels;

}
