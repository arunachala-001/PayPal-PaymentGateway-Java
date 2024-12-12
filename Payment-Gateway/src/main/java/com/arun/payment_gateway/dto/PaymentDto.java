package com.arun.payment_gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
public class PaymentDto {

    private double amount;
    private String currency;
    private String method;
    private String intent;
    private String description;
    private String returnUrl;
    private String cancelUrl;

    public PaymentDto() {
    }

    public PaymentDto(double amount, String currency, String method, String intent, String description, String returnUrl, String cancelUrl) {
        this.amount = amount;
        this.currency = currency;
        this.method = method;
        this.intent = intent;
        this.description = description;
        this.returnUrl = returnUrl;
        this.cancelUrl = cancelUrl;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

    @Override
    public String toString() {
        return "PaymentDto{" +
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                ", method='" + method + '\'' +
                ", intent='" + intent + '\'' +
                ", description='" + description + '\'' +
                ", returnUrl='" + returnUrl + '\'' +
                ", cancelUrl='" + cancelUrl + '\'' +
                '}';
    }
}
