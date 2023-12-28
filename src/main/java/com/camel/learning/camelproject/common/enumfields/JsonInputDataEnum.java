package com.camel.learning.camelproject.common.enumfields;

public enum JsonInputDataEnum {

    ITEM_NAME("itemName"),
    ITEM_DESC("itemDesc"),
    ITEM_PRICE("itemPrice"),
    ITEM_IMG("itemImg"),
    ITEM_FEATURES("itemFeatures")
    ;

    public final String field;

    JsonInputDataEnum(String field) {
        this.field = field;
    }
}
