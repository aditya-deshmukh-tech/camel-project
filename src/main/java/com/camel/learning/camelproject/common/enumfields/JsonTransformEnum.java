package com.camel.learning.camelproject.common.enumfields;

public enum JsonTransformEnum {

    MED_TITLE("med_title"),
    MED_DESC("med_desc"),
    MED_CONTENTS("med_contents"),
    MED_IMG_URL("med_img_url"),
    MED_SYMPTOMS("med_symptoms");

    public String field;

    JsonTransformEnum(String field) {
        this.field = field;
    }
}
