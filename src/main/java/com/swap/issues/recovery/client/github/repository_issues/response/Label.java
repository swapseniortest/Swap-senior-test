package com.swap.issues.recovery.client.github.repository_issues.response;

import lombok.Data;

@Data
public class Label {
    private Long id;
    private String nodeId;
    private String url;
    private String name;
    private String description;
    private String color;
    private Boolean isDefault;

}
