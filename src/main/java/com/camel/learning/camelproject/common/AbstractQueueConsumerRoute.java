package com.camel.learning.camelproject.common;

import com.camel.learning.camelproject.common.models.AbstractQueueConsumerParams;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import java.util.concurrent.TimeoutException;


public abstract class AbstractQueueConsumerRoute extends RouteBuilder {

    public abstract Class<? extends Throwable>[] getArrayOfRecoverableExceptions();

    public abstract Class<? extends Throwable>[] getArrayOfIrrecoverableExceptions();

    public abstract AbstractQueueConsumerParams getAbstractQueueConsumerParams();

    public abstract Processor getValidationAndTransformProcessor();

    public abstract Processor getErrorMsgToDeadLetterMsgProcessor();

    @Override
    public void configure() throws Exception {

        onException(getArrayOfIrrecoverableExceptions())// can add multiple exceptions which cannot be recover
                .handled(true)
                .useOriginalMessage()
                .unmarshal().json(JsonLibrary.Jackson)
                .setHeader("queueType").constant(getAbstractQueueConsumerParams().queueType())
                .process(getErrorMsgToDeadLetterMsgProcessor())
                .to(getAbstractQueueConsumerParams().deadLetterRouteString());  // can be modified to pass to dead letter database

        onException(getArrayOfRecoverableExceptions())
                .handled(true)
                .maximumRedeliveries(3)
                .redeliveryDelay(3000)
                .onRedelivery(exchange -> {
                    exchange.getIn().setHeader("one", "first retry done");
                })
                .logRetryAttempted(true)
                .log(LoggingLevel.ERROR, "retry attempts exhausted")
                .useOriginalMessage()
                .unmarshal().json(JsonLibrary.Jackson)
                .setHeader("queueType").constant(getAbstractQueueConsumerParams().queueType())
                .process(getErrorMsgToDeadLetterMsgProcessor())
                .to(getAbstractQueueConsumerParams().deadLetterRouteString());

        from(getAbstractQueueConsumerParams().queueString()).autoStartup(getAbstractQueueConsumerParams().autoStartState())
                .unmarshal().json(JsonLibrary.Jackson)
                .log("message before updating = ${body}")
                .process(getValidationAndTransformProcessor())
                .log("message after updating = ${body}")
                .process(exchange -> {
                    if (exchange.getIn().getHeader("one") != null && exchange.getIn().getHeader("one").toString().equals("first retry done")) {

                    } else {
                        throw new TimeoutException("server has timed out");
                    }
                })
                .log("after retry message is ${body}");


        from("direct:exceptionLog")
                .log("error body from exception = ${body}");
    }

}
