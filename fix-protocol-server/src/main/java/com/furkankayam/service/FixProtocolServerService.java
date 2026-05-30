package com.furkankayam.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import quickfix.*;
import quickfix.field.*;
import quickfix.fix42.ExecutionReport;
import quickfix.fix42.NewOrderSingle;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FixProtocolServerService implements Application {

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

        if (message instanceof NewOrderSingle) {
            handleNewOrderSingle((NewOrderSingle) message, sessionId);
        }
    }

    private void handleNewOrderSingle(NewOrderSingle order, SessionID sessionId) {
        try {
            Symbol symbol = order.getSymbol();
            Side side = order.getSide();
            OrderQty orderQty = order.getOrderQty();
            Price price = order.getPrice();
            ClOrdID clientOrderId = order.getClOrdID();

            log.info("[FIX 4.2 SERVER] New Order Single is processing... Symbol: {}, Side: {}, Quantity: {}, Price: {}, ClientOrderId: {}",
                    symbol.getValue(),
                    side.getValue(),
                    orderQty.getValue(),
                    price.getValue(),
                    clientOrderId.getValue()
            );

            String orderId = "SRV-" + UUID.randomUUID();
            String execId = "EXEC-" + UUID.randomUUID();

            ExecutionReport execReport = new ExecutionReport(
                    new OrderID(orderId),
                    new ExecID(execId),
                    new ExecTransType(ExecTransType.NEW),
                    new ExecType(ExecType.FILL),
                    new OrdStatus(OrdStatus.FILLED),
                    symbol,
                    side,
                    new LeavesQty(0),
                    new CumQty(orderQty.getValue()),
                    new AvgPx(price.getValue())
            );

            execReport.set(clientOrderId);

            Session.sendToTarget(execReport, sessionId);

            log.info("[FIX 4.2 SERVER] Execution Report (FILLED) has been sent!");
        } catch (Exception e) {
            log.info("[FIX 4.2 SERVER] Exception occurred while handling new order single!", e);
        }
    }
}
