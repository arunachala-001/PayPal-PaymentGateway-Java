package com.arun.payment_gateway.config;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PaypalConfig {

    @Value("${paypal.api.base-url}")
    private String baseUrl;

    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String secretKey;

    @Value("${paypal.mode}")
    private String mode;

    @Bean
    public PayPalEnvironment payPalEnvironment() {
        return new PayPalEnvironment.Sandbox(clientId, secretKey);
    }

    @Bean
    public PayPalHttpClient payPalHttpClient(PayPalEnvironment environment) {
        return new PayPalHttpClient(environment);
    }


}
