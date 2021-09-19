package enums;

public enum Platform {

    WINDOWS("win"),
    MAC("mac"),
    LINUX("linux");

    private String value;

    Platform(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue() {
        this.value = value;
    }

    public static Platform getEnumByValue(String value) {
        for (Platform mod : Platform.values()) {
            if (mod.getValue().equals(value)) {
                return mod;
            }
        }
        throw new IllegalArgumentException("No such constant with value " + value);
    }
}
