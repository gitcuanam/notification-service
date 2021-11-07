package com.pangaea.publisher.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SubscriptionResponse {
    private String url;
    private String topic;
}
