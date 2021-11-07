package com.pangaea.publisher.service;

import com.pangaea.publisher.config.kafka.KafkaProducerProperties;
import com.pangaea.publisher.dto.MessageRequest;
import com.pangaea.publisher.dto.SubscriptionCallRequest;
import com.pangaea.publisher.dto.SubscriptionRequest;
import com.pangaea.publisher.dto.SubscriptionResponse;
import com.pangaea.publisher.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class PublisherService {

    @Autowired
    private KafkaTemplate<String, MessageRequest> messageRequestKafkaTemplate;

    @Autowired
    private KafkaProducerProperties kafkaProducerProperties;

    @Autowired
    private NetworkRequestService networkRequestService;

    @Autowired
    private Util util;

    public ResponseEntity publish(Map<String, Object> payload, String topic) {
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setTopic(topic);
        messageRequest.setData(payload);

        try {
            messageRequestKafkaTemplate.setDefaultTopic(topic);
            SendResult<String, MessageRequest> sendResult = messageRequestKafkaTemplate.sendDefault(util.getSaltString(), messageRequest).get();
            RecordMetadata recordMetadata = sendResult.getRecordMetadata();
            log.info("topic = {}, body = {}", recordMetadata.topic(), messageRequest);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<SubscriptionResponse> subscribe(SubscriptionRequest subscriptionRequest, String topic) {

        HashMap<String, String> additionalHeaderParams = new HashMap() {{
            put("Content-Type", "application/json");
        }};


        SubscriptionResponse response = networkRequestService.executeRequestCustomHeaders(subscriptionRequest.getUrl(), HttpMethod.POST, SubscriptionCallRequest.builder().topic(topic).build(), SubscriptionResponse.class, additionalHeaderParams);

        return ResponseEntity.ok().body(response);
    }


}
