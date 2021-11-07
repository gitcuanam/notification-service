package com.pangaea.publisher.dto;

import lombok.Data;

import java.util.Map;

@Data
public class MessageRequest {
    private String topic;
    private Map<String, Object> data;
}
