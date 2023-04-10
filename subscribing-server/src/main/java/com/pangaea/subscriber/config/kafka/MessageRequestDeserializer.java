package com.pangaea.subscriber.config.kafka;

import java.util.Date;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pangaea.subscriber.dto.MessageRequest;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class MessageRequestDeserializer implements Deserializer<MessageRequestDeserializer> {

    // @JsonProperty("UNIQUE_KEY")
    // private Long uniqueKey;
    // @JsonProperty("EVENT_TIMESTAMP")
    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    // private Date eventTimestamp;
    // @JsonProperty("SOME_OTHER_FIELD")
    // private String someOtherField;
    @JsonProperty("topic")
    private String topic;

@Override
    public MessageRequestDeserializer deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        MessageRequestDeserializer event = null;
        try {
            // event = mapper
            //         .registerModule(new JavaTimeModule())
            //         .readValue(bytes, MessageRequestDeserializer.class);
            MessageRequest m = mapper.readValue(s, MessageRequest.class);
            System.out.println(m);
        } catch (Exception e) {
            log.error("Something went wrong during the deserialization of the MyClass: {}", e.getMessage());
        }
        return event;
    }
}
