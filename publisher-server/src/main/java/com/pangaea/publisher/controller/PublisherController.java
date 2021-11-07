package com.pangaea.publisher.controller;

import com.pangaea.publisher.dto.SubscriptionRequest;
import com.pangaea.publisher.dto.SubscriptionResponse;
import com.pangaea.publisher.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @PostMapping("/publish/{topic}")
    public ResponseEntity<String> publish(@RequestBody Map<String, Object> payload, @PathVariable("topic") String topic) {
        return this.publisherService.publish(payload, topic);
    }

    @PostMapping("/subscribe/{topic}")
    public ResponseEntity<SubscriptionResponse> subscribe(@Valid @RequestBody SubscriptionRequest subscriptionRequest, @PathVariable("topic") String topic) {
        return this.publisherService.subscribe(subscriptionRequest, topic);
    }
}
