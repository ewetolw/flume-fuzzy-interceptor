package pl.polsl.bdis.fuzzyQueries;

public enum EqualitySign {
    GreaterThan(">"),
    SmallerThan("<"),
    SmallerOrEqual("<="),
    GreaterOrEqual(">="),
    Equal("=");

    private final String text;

    EqualitySign(String s) {
        this.text = s;
    }

    @Override
    public String toString() {
        return text;
    }

    public static EqualitySign fromString(String text) {
        for (EqualitySign eqSign : EqualitySign.values()) {
            if (eqSign.text.equalsIgnoreCase(text.trim())) {
                return eqSign;
            }
        }

        throw new IllegalArgumentException("No constant with text " + text + " found");
    }

    public static EqualitySign getContrary(EqualitySign eqSign) {
        switch (eqSign) {
            case Equal:
                return EqualitySign.Equal;
            case GreaterOrEqual:
                return EqualitySign.SmallerOrEqual;
            case SmallerOrEqual:
                return EqualitySign.GreaterOrEqual;
            case GreaterThan:
                return EqualitySign.SmallerThan;
            case SmallerThan:
                return EqualitySign.GreaterThan;
            default:
                throw new IllegalArgumentException("Invalid equality sign: " + eqSign.toString());
        }
    }
}
