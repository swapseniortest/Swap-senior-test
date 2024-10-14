package com.swap.issues.recovery.client.github.repository_issues.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PullRequest {

    private String url;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("diff_url")
    private String diffUrl;

    @JsonProperty("patch_url")
    private String patchUrl;

}
