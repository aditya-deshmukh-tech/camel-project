package com.camel.learning.camelproject.learn;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class FIleRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("file://src/main/resources/jsonfiles?charset=utf-8&noop=true").autoStartup(false)
                .routeId("file-test")
                .unmarshal().json(JsonLibrary.Jackson)
                .log("content from file is = ${body}");
    }
}
