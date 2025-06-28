package com.reimbursement.expense_service.services.impl;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.reimbursement.expense_service.dtos.StatusUpdateDto;
import com.reimbursement.expense_service.services.ApprovalTopicService;
import com.reimbursement.expense_service.services.ExpenseService;

import jakarta.annotation.PostConstruct;

@Service
public class ApprovalTopicServiceImpl implements ApprovalTopicService {

    private final MqttClient mqttClient;
    private final ExpenseService expenseService;

    public ApprovalTopicServiceImpl(MqttClient mqttClient, ExpenseService expenseService) {
        this.mqttClient = mqttClient;
        this.expenseService = expenseService;
    }

    @Override
    @PostConstruct
    public void subscribe() throws MqttException {
        mqttClient.subscribe("topic/expenses", (topic, message) -> {
            Gson gson = new Gson();
            String payload = new String(message.getPayload());
            StatusUpdateDto expense_update = gson.fromJson(payload, StatusUpdateDto.class);

            String a = new String(message.getPayload());
            System.out.println("Incoming message: " + a);

            try {
                expenseService.updateStatus(expense_update.getExpense_id(), expense_update.getStatus());
            } catch (Exception e) {
                System.out.println(e);
            }
        });
    }

    @Override
    public void publishMessage(StatusUpdateDto statusUpdate) throws MqttException {

        final String TOPIC = "topic/approval";
        final Integer QOS = 2;

        Gson gson = new Gson();
        final String payload = gson.toJson(statusUpdate);
        final MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(QOS);
        mqttClient.publish(TOPIC, message);

    }

}
