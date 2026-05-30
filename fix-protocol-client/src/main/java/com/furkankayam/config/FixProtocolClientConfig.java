package com.furkankayam.config;

import com.furkankayam.service.FixProtocolClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import quickfix.*;

@Configuration
@RequiredArgsConstructor
public class FixProtocolClientConfig {

    private final FixProtocolClientProperties properties;

    @Bean
    public Application getApplication() {
        return new FixProtocolClientService();
    }

    @Bean
    public MessageStoreFactory getMessageStoreFactory(SessionSettings settings) {
        return new FileStoreFactory(settings);
    }

    @Bean
    public SessionSettings getSessionSettings() {
        SessionID sessionId = new SessionID(
                "FIX.4.2", properties.getSenderCompId(), properties.getTargetCompId()
        );

        SessionSettings settings = new SessionSettings();
        settings.setString("ConnectionType", properties.getConnectionType());
        settings.setString("StartTime", "00:00:00");
        settings.setString("EndTime", "00:00:00");
        settings.setString("UseDataDictionary", "Y");
        settings.setString("DataDictionary", "FIX42.xml");
        settings.setString("FileStorePath", "target/fix/messages");
        settings.setString("FileLogPath", "target/fix/log");
        settings.setString("TimeZone", "UTC");

        // Client-specific settings
        settings.setString("HeartBtInt", String.valueOf(properties.getHeartbeatInterval()));
        settings.setString("ReconnectInterval", "60");
        settings.setString(sessionId, "SocketConnectHost", String.valueOf(properties.getHost()));
        settings.setString(sessionId, "SocketConnectPort", String.valueOf(properties.getPort()));
        return settings;
    }

    @Bean
    public LogFactory getLogFactory(SessionSettings settings) {
        return new FileLogFactory(settings);
    }

    @Bean
    public MessageFactory getMessageFactory() {
        return new DefaultMessageFactory();
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public ThreadedSocketInitiator getThreadedSocketInitiator(
            MessageStoreFactory messageStoreFactory,
            SessionSettings sessionSettings,
            LogFactory logFactory,
            MessageFactory messageFactory
    ) throws ConfigError {

        return new ThreadedSocketInitiator(
                getApplication(),
                messageStoreFactory,
                sessionSettings,
                logFactory,
                messageFactory
        );
    }
}
