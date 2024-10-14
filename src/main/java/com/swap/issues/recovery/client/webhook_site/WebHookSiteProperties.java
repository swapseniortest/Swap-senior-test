package com.swap.issues.recovery.client.webhook_site;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("external-apis.webhook-site")
public class WebHookSiteProperties {

    private String host;
    private String id;

}
