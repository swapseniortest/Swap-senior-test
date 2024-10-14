package com.swap.issues.recovery.client.webhook_site;

import com.swap.issues.recovery.domain.dto.RepositorySnapshotDTO;
import com.swap.issues.recovery.exception.WebhookSiteException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebHookSiteClient {

    private final RestTemplate restTemplate;
    private final WebHookSiteProperties webHookSiteClient;

    public void sendWebhook(final RepositorySnapshotDTO jsonPayload) {
        var url = buildUri();
        var headers = buildHeaders();
        var request = new HttpEntity<>(jsonPayload, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
        );

        if (response.getStatusCode().isError()) {
            log.warn("Ocorreu uma falha sistêmica ao tentar enviar a requisição para o webhook. resultado: {}", response);
            throw new WebhookSiteException("ocorreu um erro ao tentar enviar a requisição para o webhook", (HttpStatus) response.getStatusCode());
        }
    }

    private URI buildUri() {
        return UriComponentsBuilder
                .fromHttpUrl(webHookSiteClient.getHost())
                .pathSegment(webHookSiteClient.getId())
                .build()
                .toUri();
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return headers;
    }
}
