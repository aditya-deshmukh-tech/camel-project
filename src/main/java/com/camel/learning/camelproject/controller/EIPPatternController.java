package com.camel.learning.camelproject.controller;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.support.DefaultExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EIPPatternController {

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private CamelContext camelContext;

    private Exchange sendAndGetResponse(Map<String, Object> data, String param, String route) {
        producerTemplate.start();

        Exchange exchange = new DefaultExchange(camelContext);

        exchange.getIn().setBody(data);

        exchange.getIn().setHeader("rte", param);

        Exchange returnExchange = producerTemplate.send("direct:"+route, exchange);

        producerTemplate.stop();

        return returnExchange;
    }


    @PostMapping("/EIP/cbr/{route}")
    public ResponseEntity<?> getResultUsingEipPatterns(@RequestBody Map<String, Object> data, @PathVariable String route) {
        return new ResponseEntity<>(sendAndGetResponse(data, route, "cbr").getIn().getBody(), HttpStatus.OK);
    }

}
