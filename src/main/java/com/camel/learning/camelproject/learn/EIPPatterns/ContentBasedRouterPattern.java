package com.camel.learning.camelproject.learn.EIPPatterns;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ContentBasedRouterPattern extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:cbr")
                .log("this log from content based router pattern")
                .choice()
                .when(simple("${header.rte} == 'one'"))
                    .to("direct:one")
                .when(simple("${header.rte} == 'two'"))
                    .to("direct:two")
                .otherwise()
                    .to("direct:three");

        from("direct:one")
                .process(exchange -> {
                    Map<String, Object> data = (Map<String, Object>) exchange.getIn().getBody();
                    data.put("FromRoute", "this is from route one");
                });

        from("direct:two")
                .process(exchange -> {
                    Map<String, Object> data = (Map<String, Object>) exchange.getIn().getBody();
                    data.put("FromRoute", "this is from route two");
                });

        from("direct:three")
                .process(exchange -> {
                    Map<String, Object> data = (Map<String, Object>) exchange.getIn().getBody();
                    data.put("FromRoute", "this is from route three");
                });
    }
}
