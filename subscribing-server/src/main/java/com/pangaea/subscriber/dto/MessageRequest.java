package com.pangaea.subscriber.dto;

import lombok.Data;

import java.util.Map;

@Data
public class MessageRequest {
    private String topic;
    private Map<String, Object> data;
}
