package com.pangaea.subscriber.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SubscriptionResponse {
    private String url;
    private String topic;
}
