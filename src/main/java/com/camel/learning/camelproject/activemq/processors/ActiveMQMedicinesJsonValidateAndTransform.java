package com.camel.learning.camelproject.activemq.processors;

import com.camel.learning.camelproject.common.enumfields.JsonInputDataEnum;
import com.camel.learning.camelproject.common.enumfields.JsonTransformEnum;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class ActiveMQMedicinesJsonValidateAndTransform implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        Map<String, Object> inputData = (Map<String, Object>) exchange.getIn().getBody();
        Map<String, Object> transformData = new HashMap<>();
        transformData.put(JsonTransformEnum.MED_TITLE.field, getString(inputData, JsonInputDataEnum.TITLE_MED.field));
        transformData.put(JsonTransformEnum.MED_DESC.field, getString(inputData, JsonInputDataEnum.DESC_MED.field));
        transformData.put(JsonTransformEnum.MED_CONTENTS.field, getStringsArray(inputData, JsonInputDataEnum.COMP_MED.field));
        transformData.put(JsonTransformEnum.MED_IMG_URL.field, getString(inputData, JsonInputDataEnum.IMG_URL_MED.field));
        transformData.put(JsonTransformEnum.MED_SYMPTOMS.field, getStringsArray(inputData, JsonInputDataEnum.SYMPTOMS_MED.field));
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
