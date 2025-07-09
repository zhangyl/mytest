package com.zyl.mytest.mq;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;


@Service
public class KafkaConsumerService {
    private final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);


    @KafkaListener(topics = "my-topic", groupId = "my-group")
    public void consume(ConsumerRecord<String, String> record, Acknowledgment ack) {
        log.info("Default Charset: {}", Charset.defaultCharset());
        Headers headers = record.headers();
        Header header = headers.lastHeader("traceId");
        if(header != null && header.value() != null) {
            String value = new String(header.value(), Charset.defaultCharset());
            log.info("KafkaConsumerService traceId={}", value);
        }
        String message = record.value();
        System.out.println("收到消息: " + message);
        ack.acknowledge();
    }
}
