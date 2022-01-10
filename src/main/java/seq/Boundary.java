package seq;

import java.util.Arrays;

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
    protected int distanceFromRootChar(final char character, final char root) {
        return charMap.index(character) - charMap.index(root);
    }
    protected char calculatedLetterFrom(final int distance, final int position) {
        final char ch = floor.charAt(position);
        final int value = charMap.index(ch) + distance;
        return charMap.character(value);
    }
    protected char calculatedCharFrom(final int distance) {
        final int index = distance - 1;
        return charMap.character(distance);
    }
    public boolean isPartOfCharMap() {
        return Arrays.stream(floor.split("")).allMatch(s -> charMap.contains(s.charAt(0)))
                && Arrays.stream(ceiling.split("")).allMatch(s -> charMap.contains(s.charAt(0)));
    }
}
