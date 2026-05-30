package com.furkankayam.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "fix.server")
public class FixProtocolServerProperties {

    private int port;
    private String senderCompId;
    private String targetCompId;
    private String connectionType;
}
