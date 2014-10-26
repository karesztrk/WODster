package model.training;

/**
 *
 */
public enum Category {

    SINGLET,
    COUPLET,
    TRIPLET,
    EMOM,
    CHIPPER,
    AMRAP;

    public String getValue() {
        return name();
    }
}
