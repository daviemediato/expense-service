package com.reimbursement.expense_service.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class MqttConfig {

    private static final String URI = "tcp://localhost:1883";
    private static final String PUBLISHERID = "expense-service-client";

    @Bean
    public MqttClient mqttClient() throws MqttException {

        MqttClient client = new MqttClient(URI, PUBLISHERID);
        MqttConnectOptions options = new MqttConnectOptions();

        options.setAutomaticReconnect(true);
        client.connect(options);

        return client;
    }
}
