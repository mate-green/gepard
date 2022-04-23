package com.matecko.search.seq;

import java.util.List;

/**
 * CharMap represents character map that defines the order of characters used for searching
 * <p>
 * <strong>Example: </strong> If CharMap is defined like
 * <pre>
 *     <code>
 *         CharMap.of('C', 'A')
 *     </code>
 * </pre>
 * the first character used for comparison would be C, then A means that expected sequence would be CC, CA, AC, AA.
 */
public class CharMap {
    /**
     * Numeric representation (0-9)
     */
    public static final CharMap NUM =
            CharMap.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    /**
     * Alphabetical representation (A-Z)
     */
    public static final CharMap ALPHA =
            CharMap.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
                    'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
    /**
     * Numeric and alphabetical representation (0-9A-Z)
     */
    public static final CharMap NUM_FIRST_THEN_ALPHA =
            CharMap.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                    'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
    /**
     * Alphabetical and numeric representation (A-Z0-9)
     */
    public static final CharMap ALPHA_FIRST_THEN_NUM =
            CharMap.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
                    'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    private final char[] arr;

    private CharMap(final char[] arr) {
        this.arr = arr;
    }

    /**
     * Constructs a CharMap
     * @param chars The array of allowed characters
     * @return A new CharMap instance
     */
    public static CharMap of(final char ... chars) {
        return new CharMap(chars);
    }
    /**
     * Constructs a CharMap
     * @param chars The list of allowed characters
     * @return A new CharMap instance
     */
    public static CharMap of(final List<Character> chars) {
        char[] arr = new char[chars.size()];
        for (int i = 0; i < chars.size(); i++)
            arr[i] = chars.get(i);
        return new CharMap(arr);
    }

    /**
     * Returns character based on its index
     * @param index character index
     * @return character
     * @throws IllegalArgumentException if index overflows array length
     */
    protected char character(int index) {
        if (index >= arr.length)
                throw new IllegalArgumentException("Character outside of CharMap");
        return arr[index];
    }

    /**
     * Returns index of character provided
     * @param character character provided
     * @return character index
     * @throws IllegalArgumentException if character provided is not part of CharMap
     */
    protected int index(char character) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == character) {
                return i;
            }
        }
        throw new IllegalArgumentException(String.format("Character %c is not present in CharMap", character));
    }

    /**
     * Returns if this CharMap contains character provided
     * @param character character provided
     * @return true, if this character is part of CharMap, false otherwise
     */
    protected boolean contains(char character) {
        for (char c : arr) {
            if (c == character) {
                return true;
            }
        }
        return false;
    }

    /**
     * The size of CharMap
     * @return count of characters included
     */
    protected int size() {
        return this.arr.length;
    }

    /**
     * Defaults the CharMap, if this one is not provided. It takes floor and ceiling params and creates either alphabetic,
     * numeric, alphanumeric starting with alphabet or alphanumeric starting with numbers
     * @param floor     The lowest possible sequence in sequence list (starting point)
     * @param ceiling   The highest possible sequence in sequence list
     * @return new default {@link CharMap}
     * @throws IllegalArgumentException if boundary contains any character that is neither alphabetical nor numeric
     */
    protected static CharMap defaulted(String floor, String ceiling) {
        if (floor.matches("[A-Z]+") && ceiling.matches("[A-Z]+")) {
            return CharMap.ALPHA;
        }
        if (floor.matches("\\d+") && ceiling.matches("\\d+")) {
            return CharMap.NUM;
        }
        if (floor.matches("[A-Z]+") && ceiling.matches("\\d+")) {
            return CharMap.ALPHA_FIRST_THEN_NUM;
        }
       return CharMap.NUM_FIRST_THEN_ALPHA;
    }
}
