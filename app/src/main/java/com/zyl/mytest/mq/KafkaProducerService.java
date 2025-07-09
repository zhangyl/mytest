package com.zyl.mytest.mq;

import io.micrometer.tracing.CurrentTraceContext;
import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


@Service
public class KafkaProducerService {

    private final Tracer tracer;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final AdminClient adminClient;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate, KafkaAdmin kafkaAdmin, Tracer tracer) {
        this.kafkaTemplate = kafkaTemplate;
        this.adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());
        this.tracer = tracer;
    }

    public void sendMessage(String topicName, String message) {

        DescribeTopicsResult describeTopicsResult = adminClient.describeTopics(Collections.singletonList(topicName));
        try {
            TopicDescription topicDescription = describeTopicsResult.topicNameValues().get(topicName).get();
            int partitionSize = topicDescription.partitions().size();

            int partition = 0;
            String key = "";

            if(partitionSize > 1) {
                partition = message.hashCode() % partitionSize;
            }
            long timestamp = System.currentTimeMillis();

            List<Header> headers = new LinkedList<>();
            headers.add(new Header() {
                @Override
                public String key() {
                    return "traceId";
                }
                @Override
                public byte[] value() {
                    CurrentTraceContext context = tracer.currentTraceContext();
                    TraceContext traceContext = context.context();
                    if(traceContext != null) {
                        return traceContext.traceId().getBytes(StandardCharsets.UTF_8);
                    }
                    return new byte[0];
                }
            });

            ProducerRecord<String, String> record = new ProducerRecord<>(topicName, partition, timestamp, key, message);
            record.headers().add(new Header() {
                @Override
                public String key() {
                    return "traceId";
                }

                @Override
                public byte[] value() {
                    CurrentTraceContext context = tracer.currentTraceContext();
                    TraceContext traceContext = context.context();
                    if (traceContext != null) {
                        return traceContext.traceId().getBytes(StandardCharsets.UTF_8);
                    }
                    return new byte[0];
                }
            });

            kafkaTemplate.send(record);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get partition count for topic: " + topicName, e);
        }



    }
}
