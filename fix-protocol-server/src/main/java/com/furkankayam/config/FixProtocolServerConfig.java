package com.furkankayam.config;

import com.furkankayam.service.FixProtocolServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import quickfix.*;

@Configuration
@RequiredArgsConstructor
public class FixProtocolServerConfig {

    private final FixProtocolServerProperties properties;

    @Bean
    public Application getApplication() {
        return new FixProtocolServerService();
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
        settings.setString("SocketAcceptPort", String.valueOf(properties.getPort()));
        settings.setString(sessionId, "BeginString", "FIX.4.2");
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
    public ThreadedSocketAcceptor getThreadedSocketAcceptor(
            MessageStoreFactory messageStoreFactory,
            SessionSettings sessionSettings,
            LogFactory logFactory,
            MessageFactory messageFactory
    ) throws ConfigError {

        return new ThreadedSocketAcceptor(
                getApplication(),
                messageStoreFactory,
                sessionSettings,
                logFactory,
                messageFactory
        );
    }
}
