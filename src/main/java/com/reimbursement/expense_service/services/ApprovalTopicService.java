package com.reimbursement.expense_service.services;

import org.eclipse.paho.client.mqttv3.MqttException;

import com.reimbursement.expense_service.dtos.StatusUpdateDto;

public interface ApprovalTopicService {

    public void publishMessage(StatusUpdateDto statusUpdate) throws MqttException;

    public void subscribe() throws MqttException;

}
