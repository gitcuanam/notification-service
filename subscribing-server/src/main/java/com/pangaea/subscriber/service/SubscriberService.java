package com.pangaea.subscriber.service;

import com.pangaea.subscriber.config.kafka.ConsumerConfiguration;
import com.pangaea.subscriber.dto.MessageRequest;
import com.pangaea.subscriber.dto.SubscriptionRequest;
import com.pangaea.subscriber.dto.SubscriptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Collections;

@Slf4j
@Service
public class SubscriberService {

    @Autowired
    private ConsumerConfiguration consumerConfiguration;

    @KafkaListener(topics = "testAuto")
    public void onReceiving(MessageRequest messageRequest, @Header(KafkaHeaders.OFFSET) Integer offset,
                            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Processing topic = {}, messageRequest = {}",
                topic, partition, offset, messageRequest);
    }

    public ResponseEntity<SubscriptionResponse> consumeAndCreateSubscription(@Valid @RequestBody SubscriptionRequest subscriptionRequest) {
        try {
            prepareConsumer(subscriptionRequest.getTopic());
            return ResponseEntity.ok().body(SubscriptionResponse.builder().topic(subscriptionRequest.getTopic()).build());
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

    }


    public Consumer<Long, Object> createConsumerAndSubscription(String topic) {
        log.info("topic requested {}", topic);
        // Create the consumer using props.
        final Consumer<Long, Object> consumer =
                new KafkaConsumer<>(consumerConfiguration.consumerProps());
        log.info("consumer topics {}", consumer.listTopics());
        // Subscribe to the topic.
        consumer.subscribe(Collections.singletonList(topic));
        return consumer;
    }

    public void prepareConsumer(String topic) throws InterruptedException {
        final Consumer<Long, Object> consumer = createConsumerAndSubscription(topic);

        ConsumerRecords<Long, Object> records = consumer.poll(100);
        for (ConsumerRecord<Long, Object> record : records)
            System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
        consumer.commitAsync();
        consumer.close();
    }

}
