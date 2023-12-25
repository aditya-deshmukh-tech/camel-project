package com.camel.learning.camelproject.common.enumfields;

public enum JsonInputDataEnum {

    TITLE_MED("titleMed"),
    DESC_MED("descMed"),
    COMP_MED("compositionMed"),
    IMG_URL_MED("imgUrlMed"),
    SYMPTOMS_MED("symptomsMed")
    ;

    public final String field;

    JsonInputDataEnum(String field) {
        this.field = field;
    }
}
