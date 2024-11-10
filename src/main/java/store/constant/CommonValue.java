package store.constant;

public enum CommonValue {
    ZERO(0),
    ONE(1),
    TWO(2);

    private final int value;

    CommonValue(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
