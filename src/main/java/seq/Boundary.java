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
    public int distanceFromRootChar(final char character, final char root) {
        return charMap.index(character) - charMap.index(root);
    }
    public static Boundary withRootFloor(final Boundary current) {
        return new Boundary(
                new String(new char[current.floor().length()]).replace('\0', current.charMap().character(0)),
                current.ceiling(),
                current.charMap()
        );
    }
    public char calculatedCharFrom(final int distance) {
        return charMap.character(distance);
    }
    public boolean isPartOfCharMap() {
        return Arrays.stream(floor.split("")).allMatch(s -> charMap.contains(s.charAt(0)))
                && Arrays.stream(ceiling.split("")).allMatch(s -> charMap.contains(s.charAt(0)));
    }
}
