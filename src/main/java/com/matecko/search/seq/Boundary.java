package com.matecko.search.seq;

import java.util.Arrays;

/**
 * Defines boundaries of sequence
 */
public final class Boundary {
    private final String floor;
    private final String ceiling;
    private final CharMap charMap;

    /**
     * Constructs new boundary
     * @param floor     The lowest possible sequence in sequence list (starting point)
     * @param ceiling   The highest possible sequence in sequence list
     * @param charMap   {@link CharMap} ordered list of allowed characters
     */
    public Boundary(String floor, String ceiling, CharMap charMap) {
        this.floor = floor;
        this.ceiling = ceiling;
        this.charMap = charMap;
    }

    /**
     * Constructs new boundary with default CharMap
     * @param floor     The lowest possible sequence in sequence list (starting point)
     * @param ceiling   The highest possible sequence in sequence list
     */
    public Boundary(String floor, String ceiling) {
        this(floor, ceiling, CharMap.defaulted(floor, ceiling));
    }

    /**
     * Ceiling value
     * @return ceiling
     */
    public String ceiling() {
        return ceiling;
    }

    /**
     * Floor value
     * @return floor
     */
    public String floor() {
        return floor;
    }

    /**
     * CharMap used in boundary
     * @return {@link CharMap}
     */
    protected CharMap charMap() {
        return charMap;
    }

    /**
     * Calculates distance from root character defined via CharMap
     * @param character current character
     * @param root root character
     * @return distance from root character
     */
    protected int distanceFromRootChar(final char character, final char root) {
        return charMap.index(character) - charMap.index(root);
    }

    /**
     * Creates new instance of boundary based on current instance, but with floor made of root character
     * @param current current {@link Boundary}
     * @return new {@link Boundary} instance
     */
    protected static Boundary withRootFloor(final Boundary current) {
        return new Boundary(
                new String(new char[current.floor().length()]).replace('\0', current.charMap().character(0)),
                current.ceiling(),
                current.charMap()
        );
    }

    /**
     * Takes character from CharMap based on its distance from root
     * @param distance distance from root
     * @return character taken from CharMap
     */
    protected char calculatedCharFrom(final int distance) {
        return charMap.character(distance);
    }

    /**
     * Returns if boundary characters are part of CharMap provided
     * @return true, if boundary is part of CharMap, false otherwise
     */
    protected boolean isPartOfCharMap() {
        return Arrays.stream(floor.split("")).allMatch(s -> charMap.contains(s.charAt(0)))
                && Arrays.stream(ceiling.split("")).allMatch(s -> charMap.contains(s.charAt(0)));
    }
}
