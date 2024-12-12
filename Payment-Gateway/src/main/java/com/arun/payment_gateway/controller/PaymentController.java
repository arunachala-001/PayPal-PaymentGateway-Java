package com.arun.payment_gateway.controller;

import com.arun.payment_gateway.dto.PaymentDto;
import com.arun.payment_gateway.service.PayPalService;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import com.paypal.orders.OrderCaptureRequest;
import com.paypal.orders.OrdersCaptureRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    @Autowired
    private PayPalService payPalService;

    private final String SUCCESS_URL = "http://localhost:8080/payment/success";
    private final String CANCEL_URL = "http://localhost:8080/payment/cancel";

    Logger log = LoggerFactory.getLogger(PaymentController.class);

    private String approvalURL;

    @PostMapping("/create")
    public String createOrderInterface(@RequestBody PaymentDto paymentDto) {
        paymentDto.setReturnUrl(SUCCESS_URL);
        paymentDto.setCancelUrl(CANCEL_URL);
        try {
            Order order = payPalService.createPayment(paymentDto);
            log.info("Order Status {} :"+order.status());
            log.info("Order ID {} :"+ order.id());

            for(LinkDescription link: order.links()) {
                if(link.rel().equals("approve")) {
                    approvalURL = link.href();
                    return "redirect:" + approvalURL;
                }

            }
            return "redirect:"+CANCEL_URL;

//            return ResponseEntity.ok(order.id());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping(value = SUCCESS_URL)
    public String Success(@RequestParam("token") String token) {
        try{
            OrdersCaptureRequest captureRequest = new OrdersCaptureRequest(token);
            Order order = payPalService.executePayment(captureRequest);
            log.info(order.status());
            log.info(order.id());
            return "Payment Got Successful!";

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/cancel")
    public String Failed() {
        return "Payment got Failed!";
    }
}
