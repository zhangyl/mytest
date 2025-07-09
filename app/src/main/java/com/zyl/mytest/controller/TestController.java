package com.zyl.mytest.controller;

import com.zyl.mytest.mq.KafkaProducerService;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {
    private final Logger log = LoggerFactory.getLogger(TestController.class);
    private final Tracer tracer;

    public TestController(Tracer tracer) {
        this.tracer = tracer;
    }
    @Resource
    KafkaProducerService kafkaProducerService;
    @GetMapping("/test/{message}")
    @ResponseBody
    public List<String> test(@PathVariable(name = "message") String message) {
        List<String> list = new ArrayList<>();

        list.add("A");
        list.add("B");
        String msg = "this is test message";
        if(message != null) {
            msg = message;
        }
        Span span = tracer.currentSpan();
        if(span != null) {
            log.info("traceId={}", span.context().toString());
        }
        kafkaProducerService.sendMessage("my-topic", msg);
        return list;
    }
}
