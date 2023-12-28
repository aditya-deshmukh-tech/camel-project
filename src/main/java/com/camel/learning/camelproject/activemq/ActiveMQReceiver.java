package com.camel.learning.camelproject.activemq;


import com.camel.learning.camelproject.activemq.processors.ActiveMQDataJsonValidateAndTransform;
import com.camel.learning.camelproject.common.AbstractQueueConsumerRoute;
import com.camel.learning.camelproject.common.models.AbstractQueueConsumerParams;
import com.camel.learning.camelproject.common.processors.AbstractErrMsgProcessor;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeoutException;

@Component
public class ActiveMQReceiver extends AbstractQueueConsumerRoute {

    @Autowired
    private ActiveMQDataJsonValidateAndTransform activeMQDataJsonValidateAndTransform;

    @Autowired
    private AbstractErrMsgProcessor abstractErrMsgProcessor;

    @Value("${activemq.queue.name}")
    private String queueTopic;

    @Value("${activemq.autoStart}")
    private boolean autoStartState;

    @Override
    public AbstractQueueConsumerParams getAbstractQueueConsumerParams() {
        return new AbstractQueueConsumerParams("direct:exceptionLog", "activemq:queue:" + queueTopic, "activeMQ", autoStartState);
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
        return activeMQDataJsonValidateAndTransform;
    }

    @Override
    public Processor getErrorMsgToDeadLetterMsgProcessor() {
        return abstractErrMsgProcessor;
    }

}
