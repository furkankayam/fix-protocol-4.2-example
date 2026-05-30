package com.furkankayam.controller;

import com.furkankayam.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/fix-client")
@RequiredArgsConstructor
public class FixProtocolClientController {

    private final OrderService orderService;

    @PostMapping("/send-order")
    public void sendOrder() {
        orderService.sendOrder();
    }
}
