package com.example.orderservice.messagequeue;

import com.example.orderservice.vo.ResponseOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j @RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate; // 메시지를 보내기 위한 빈

    public void send(String topic, ResponseOrder orderDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInString = "";

        try {
            jsonInString = objectMapper.writeValueAsString(orderDto);
        } catch (JsonProcessingException exception) {
            exception.printStackTrace();
        }

        // topic 전송
        kafkaTemplate.send(topic, jsonInString);
        log.info("Kafka Producer sent data from the Order microservice = {}", orderDto);
    }
}
