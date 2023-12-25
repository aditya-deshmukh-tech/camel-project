package com.camel.learning.camelproject.activemq;

import com.camel.learning.camelproject.activemq.processors.ActiveMQMedicinesJsonValidateAndTransform;
import com.camel.learning.camelproject.common.AbstractQueueConsumerRoute;
import com.camel.learning.camelproject.common.models.AbstractQueueConsumerParams;
import com.camel.learning.camelproject.common.processors.AbstractErrMsgProcessor;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeoutException;

//@Component
public class ActiveMQReceiver extends AbstractQueueConsumerRoute {

    @Autowired
    private ActiveMQMedicinesJsonValidateAndTransform activeMQMedicinesJsonValidateAndTransform;

    @Autowired
    private AbstractErrMsgProcessor abstractErrMsgProcessor;

    @Override
    public AbstractQueueConsumerParams getAbstractQueueConsumerParams() {
        return new AbstractQueueConsumerParams("direct:exceptionLog", "activemq:queue:medicines", "activeMQ", false);
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
        return activeMQMedicinesJsonValidateAndTransform;
    }

    @Override
    public Processor getErrorMsgToDeadLetterMsgProcessor() {
        return abstractErrMsgProcessor;
    }

}
