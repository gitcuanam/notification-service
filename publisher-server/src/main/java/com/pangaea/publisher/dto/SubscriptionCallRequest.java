package com.pangaea.publisher.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SubscriptionCallRequest {
    private String topic;
}
