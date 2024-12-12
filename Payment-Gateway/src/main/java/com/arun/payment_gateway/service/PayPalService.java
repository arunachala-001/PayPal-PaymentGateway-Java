package com.arun.payment_gateway.service;

import com.arun.payment_gateway.dto.PaymentDto;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PayPalService {

    @Autowired
    private PayPalHttpClient payPalClient;

    public Order createPayment(PaymentDto paymentDto) throws IOException {

        OrderRequest request = new OrderRequest();
        request.checkoutPaymentIntent(paymentDto.getIntent());

        AmountWithBreakdown amount = new AmountWithBreakdown();
        amount.currencyCode(paymentDto.getCurrency());
        double total = BigDecimal.valueOf(paymentDto.getAmount())
                .setScale(2, RoundingMode.HALF_UP).doubleValue();
        amount.value(String.format("%.2f", total));

        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest();
        purchaseUnitRequest.description(paymentDto.getDescription());
        purchaseUnitRequest.amountWithBreakdown(amount);

        request.purchaseUnits(List.of(purchaseUnitRequest));

        ApplicationContext context = new ApplicationContext()
                .returnUrl(paymentDto.getReturnUrl())
                .cancelUrl(paymentDto.getCancelUrl());

        request.applicationContext(context);

        OrdersCreateRequest createrequest = new OrdersCreateRequest()
                .requestBody(request);

        HttpResponse<Order> response = payPalClient.execute(createrequest);
        Order order = response.result();

        return order;
    }

    public Order executePayment(OrdersCaptureRequest captureRequest) throws IOException {
        HttpResponse<Order> response = payPalClient.execute(captureRequest);
        return response.result();
    }
}
