package com.pangaea.subscriber.config.kafka;

import java.util.Map;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pangaea.subscriber.dto.MessageRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomDeserializer implements Deserializer<MessageRequest> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public MessageRequest deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Deserializing...");
            return objectMapper.readValue(new String(data, "UTF-8"), MessageRequest.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to MessageDto");
        }
    }

    @Override
    public void close() {
    }
}
