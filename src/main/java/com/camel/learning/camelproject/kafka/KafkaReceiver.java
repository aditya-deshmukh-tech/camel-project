package com.camel.learning.camelproject.kafka;

import com.camel.learning.camelproject.common.AbstractQueueConsumerRoute;
import com.camel.learning.camelproject.common.models.AbstractQueueConsumerParams;
import com.camel.learning.camelproject.common.processors.AbstractErrMsgProcessor;
import com.camel.learning.camelproject.kafka.processors.KafkaDataJsonValidateAndTransform;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.TimeoutException;


//@Component
public class KafkaReceiver extends AbstractQueueConsumerRoute {

    @Autowired
    private KafkaDataJsonValidateAndTransform kafkaDataJsonValidateAndTransform;

    @Autowired
    private AbstractErrMsgProcessor abstractErrMsgProcessor;

    @Value("${kafka.autoStart}")
    private boolean autoStartState;

    @Value("${kafka.topic}")
    private String kafkaTopic;

    @Value("${kafka.url}")
    private String kafkaUri;

    @Override
    public AbstractQueueConsumerParams getAbstractQueueConsumerParams() {
        return new AbstractQueueConsumerParams("direct:exceptionLog", "kafka:"+kafkaTopic+"?brokers="+kafkaUri, "kafka", autoStartState);
    }

    @Override
    public Class<? extends Throwable>[] getArrayOfRecoverableExceptions() {
        return new Class[]{TimeoutException.class};
    }

    @Override
    public Class<? extends Throwable>[] getArrayOfIrrecoverableExceptions() {
        return new Class[]{IllegalArgumentException.class};
    }

    @Override
    public Processor getValidationAndTransformProcessor() {
        return kafkaDataJsonValidateAndTransform;
    }

    @Override
    public Processor getErrorMsgToDeadLetterMsgProcessor() {
        return abstractErrMsgProcessor;
    }
}
