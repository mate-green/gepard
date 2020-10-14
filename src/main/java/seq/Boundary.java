package seq;

public final class Boundary {
    private final String floor;
    private final String ceiling;
    private final CharMap charMap;

    public Boundary(String floor, String ceiling, CharMap charMap) {
        this.floor = floor;
        this.ceiling = ceiling;
        this.charMap = charMap;
    }
    public Boundary(String floor, String ceiling) {
        this(floor, ceiling, CharMap.defaulted(floor, ceiling));
    }

    public String ceiling() {
        return ceiling;
    }
    public String floor() {
        return floor;
    }

    public CharMap charMap() {
        return charMap;
    }
    protected int distanceFromRootChar(final char character, final int charAt) {
        final char root = floor.charAt(charAt);
        return charMap.index(character) - charMap.index(root);
    }
    protected char calculatedLetterFrom(final int distance, final int charAt) {
        final char root = this.floor.charAt(charAt);
        final char top = this.ceiling.charAt(charAt);
        final int value = charMap.index(root) + distance;
        if (value > charMap.index(top))
            throw new IllegalArgumentException(String.format("distance from floor exceeds ceiling value at index %d", charAt));
        return charMap.character(value);
    }
}
