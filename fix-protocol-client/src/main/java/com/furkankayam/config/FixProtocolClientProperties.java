package com.furkankayam.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "fix.client")
public class FixProtocolClientProperties {

    private int port;
    private String host;
    private String senderCompId;
    private String targetCompId;
    private String connectionType;
    private int heartbeatInterval;
}
