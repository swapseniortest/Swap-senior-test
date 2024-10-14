package com.swap.issues.recovery.client.github;

import com.swap.issues.recovery.client.github.repository_commits.exception.GitHubCommitsException;
import com.swap.issues.recovery.client.github.repository_commits.response.CommitResponse;
import com.swap.issues.recovery.client.github.repository_issues.exception.GitHubIssuesException;
import com.swap.issues.recovery.client.github.repository_issues.response.IssueResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GitHubClient {

    private final RestTemplate restTemplate;
    private final GithubProperties githubProperties; // Classe de configuração para armazenar URL e Token

    // Busca as issues do repositório
    public List<IssueResponse> getIssues(final String owner, final String repo) {
        var url = buildUri(owner, repo, githubProperties.getPaths().getGithubIssuesApi());
        try {
            return makeRequest(url, new ParameterizedTypeReference<List<IssueResponse>>() {
            });
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            var statusCode = (HttpStatus) e.getStatusCode();
            var responseBody = e.getResponseBodyAsString();

            log.error("Erro na requisição GitHub - Status: {} - Mensagem: {}", statusCode, responseBody, e);
            throw new GitHubIssuesException("Erro ao buscar issues: " + responseBody, statusCode);
        }
    }

    // Busca commits e contribuidores do repositório
    public List<CommitResponse> getContributorsFromCommits(final String owner, final String repo) {
        URI url = buildUri(owner, repo, githubProperties.getPaths().getGithubCommitsApi());
        try {
            return makeRequest(url, new ParameterizedTypeReference<List<CommitResponse>>() {
            });
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            var statusCode = (HttpStatus) e.getStatusCode();
            var responseBody = e.getResponseBodyAsString();

            log.error("Erro na requisição GitHub - Status: {} - Mensagem: {}", statusCode, responseBody, e);
            throw new GitHubCommitsException("Erro ao buscar commits para o repositório: " + repo, statusCode);
        }
    }


    private <T> List<T> makeRequest(URI url, ParameterizedTypeReference<List<T>> responseType) {
        var headers = buildHeaders();
        var entity = new HttpEntity<>(headers);
        var response = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
        return response.getBody();
    }

    private URI buildUri(String owner, String repo, String path) {
        return UriComponentsBuilder
                .fromHttpUrl(githubProperties.getHost())
                .pathSegment(owner, repo, path)
                .queryParam("per_page", githubProperties.getValuesPerPages())
                .queryParam("page", githubProperties.getPageNumber())
                .build()
                .toUri();
    }

    // Método para construir os headers da requisição
    private HttpHeaders buildHeaders() {
        var headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, "application/vnd.github+json");

        //Descomentar para testes locais
        //headers.set(HttpHeaders.USER_AGENT, githubProperties.getUser());
        //headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + githubProperties.getToken());
        headers.set("X-GitHub-Api-Version", "2022-11-28");
        return headers;
    }
}

