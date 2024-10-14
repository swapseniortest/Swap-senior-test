package com.swap.issues.recovery.client.github;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("external-apis.github")
public class GithubProperties {

    //Github não deixa commitar
    //private String user;
    //private String token;
    private String host;
    private Integer valuesPerPages;
    private Integer pageNumber;
    private GithubPaths paths;

    @Getter
    @Setter
    public static class GithubPaths {
        private String githubIssuesApi;
        private String githubCommitsApi;
    }
}
