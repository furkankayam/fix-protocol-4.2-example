package com.furkankayam.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.field.*;
import quickfix.fix42.NewOrderSingle;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class OrderService {

    public void sendOrder() {
        SessionID sessionId = new SessionID(
                "FIX 4.2",
                "MY_CLIENT_APP",
                "EXCHANGE_SERVER"
        );

        NewOrderSingle order = new NewOrderSingle(
                new ClOrdID(UUID.randomUUID().toString()),
                new HandlInst('1'),
                new Symbol("THYAO"),
                new Side(Side.BUY),
                new TransactTime(LocalDateTime.now()),
                new OrdType(OrdType.LIMIT)
        );
        order.set(new OrderQty(100));
        order.set(new Price(250.50));

        try {
            if (Session.doesSessionExist(sessionId)) {
                Session.sendToTarget(order, sessionId);
                log.info("[FIX 4.2 CLIENT] Order has been sent successfully! | sessionId={}, order={}", sessionId, order);
            }
        } catch (Exception e) {
            log.error("[FIX 4.2 CLIENT] Exception occurred while sending order!", e);
        }
    }
}
