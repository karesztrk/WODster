package model.training;

import common.IndexedEnum;

public enum ResultMeasurementType implements IndexedEnum {
    UNKNOWN,
    TIME,
    REPETITION,
    WEIGHT;

    public String getValue() {
        return name();
    }
}
