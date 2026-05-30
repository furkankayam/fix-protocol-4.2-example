package com.furkankayam.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import quickfix.*;
import quickfix.fix42.ExecutionReport;

@Service
@RequiredArgsConstructor
@Slf4j
public class FixProtocolClientService implements Application {

    @Override
    public void onCreate(SessionID sessionId) {
        log.info("onCreate executed. | sessionId={}", sessionId);
    }

    @Override
    public void onLogon(SessionID sessionId) {
        log.info("onLogon executed. | sessionId={}", sessionId);
    }

    @Override
    public void onLogout(SessionID sessionId) {
        log.info("onLogout executed. | sessionId={}", sessionId);
    }

    @Override
    public void toAdmin(Message message, SessionID sessionId) {
        log.info("toAdmin executed. | sessionId={}, message={}", sessionId, message);
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        log.info("fromAdmin executed. | sessionId={}, message={}", sessionId, message);
    }

    @Override
    public void toApp(Message message, SessionID sessionId) throws DoNotSend {
        log.info("toApp executed. | sessionId={}, message={}", sessionId, message);
    }

    @Override
    public void fromApp(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        log.info("fromApp executed. | sessionId={}, message={}", sessionId, message);

        if (message instanceof ExecutionReport) {
            log.info("[FIX 4.2 CLIENT] Execution Report: {}", ((ExecutionReport) message).toString());
        }
    }
}
