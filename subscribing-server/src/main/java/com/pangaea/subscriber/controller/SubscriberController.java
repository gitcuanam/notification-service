package com.pangaea.subscriber.controller;

import com.pangaea.subscriber.dto.SubscriptionRequest;
import com.pangaea.subscriber.dto.SubscriptionResponse;
import com.pangaea.subscriber.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class SubscriberController {

    @Autowired
    private SubscriberService subscriberService;

    @PostMapping("/test1")
    public ResponseEntity<SubscriptionResponse> testOne(@Valid @RequestBody SubscriptionRequest subscriptionRequest) {
        return this.subscriberService.consumeAndCreateSubscription(subscriptionRequest);
    }

    @PostMapping("/test2")
    public ResponseEntity<SubscriptionResponse> testTwo(@Valid @RequestBody SubscriptionRequest subscriptionRequest) {
        return this.subscriberService.consumeAndCreateSubscription(subscriptionRequest);
    }

}
