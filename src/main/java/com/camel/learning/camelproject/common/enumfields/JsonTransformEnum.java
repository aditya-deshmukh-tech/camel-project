package com.camel.learning.camelproject.common.enumfields;

public enum JsonTransformEnum {

    TITLE_ITEM("item_title"),
    DESC_ITEM("item_desc"),
    PRICE_ITEM("item_price"),
    IMG_ITEM("item_img_url"),
    FEATURES_ITEM("item_features");
    public String field;

    JsonTransformEnum(String field) {
        this.field = field;
    }
}
