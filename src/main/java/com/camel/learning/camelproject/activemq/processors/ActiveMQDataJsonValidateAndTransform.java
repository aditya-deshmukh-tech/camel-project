package com.camel.learning.camelproject.activemq.processors;

import com.camel.learning.camelproject.common.enumfields.JsonInputDataEnum;
import com.camel.learning.camelproject.common.enumfields.JsonTransformEnum;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class ActiveMQDataJsonValidateAndTransform implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        Map<String, Object> inputData = (Map<String, Object>) exchange.getIn().getBody();
        Map<String, Object> transformData = new HashMap<>();
        transformData.put(JsonTransformEnum.TITLE_ITEM.field, getString(inputData, JsonInputDataEnum.ITEM_NAME.field));
        transformData.put(JsonTransformEnum.DESC_ITEM.field, getString(inputData, JsonInputDataEnum.ITEM_DESC.field));
        transformData.put(JsonTransformEnum.IMG_ITEM.field, getStringsArray(inputData, JsonInputDataEnum.ITEM_IMG.field));
        transformData.put(JsonTransformEnum.PRICE_ITEM.field, getString(inputData, JsonInputDataEnum.ITEM_PRICE.field));
        transformData.put(JsonTransformEnum.FEATURES_ITEM.field, getStringsArray(inputData, JsonInputDataEnum.ITEM_FEATURES.field));
        exchange.getIn().setBody(transformData);
    }

    private String getString(Map<String, Object> data, String inputString) {
        if (data.containsKey(inputString)) {
            return (String) data.get(inputString);
        } else {
            throw new IllegalArgumentException("no valid [" + inputString + "] key present in input data");
        }
    }

    private String[] getStringsArray(Map<String, Object> data, String inputString) {
        if (data.containsKey(inputString)) {
            if (data.get(inputString).toString().contains("+")) {
                return data.get(inputString).toString().split("\\+");
            } else {
                return data.get(inputString).toString().split(",");
            }
        } else {
            throw new IllegalArgumentException("no valid [" + inputString + "] key present in input data");
        }
    }
}
