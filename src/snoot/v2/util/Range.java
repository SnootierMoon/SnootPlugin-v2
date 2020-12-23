package snoot.v2.util;

public class Range<T extends Comparable<T>> {

    private final T minimum, maximum;

    private Range(T minimum, T maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public static <U extends Comparable<U>> Range<U> between(U minimum, U maximum) { return new Range<>(minimum, maximum); }
    public static <U extends Comparable<U>> Range<U> greaterThan(U minimum) { return new Range<>(minimum, null); }
    public static <U extends Comparable<U>> Range<U> lessThan(U maximum) { return new Range<>(null, maximum); }

    public boolean contains(T value) { return (minimum == null || minimum.compareTo(value) <= 0) && (maximum == null || maximum.compareTo(value) >= 0);
    }

    public String getDescription() {
        if (minimum != null && maximum != null) {
            return "in the range " + minimum + " to " + maximum;
        } else if (minimum != null) {
            return ">" + minimum;
        } else if (maximum != null) {
            return "<" + maximum;
        }
        return "";
    }

}
