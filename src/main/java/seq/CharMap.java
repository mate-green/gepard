package seq;

public final class CharMap {
    public static final CharMap NUM =
            CharMap.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    public static final CharMap ALPHA =
            CharMap.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
                    'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
    public static final CharMap NUM_FIRST_THEN_ALPHA =
            CharMap.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                    'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
    public static final CharMap ALPHA_FIRST_THEN_NUM =
            CharMap.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
                    'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    private final char[] arr;

    private CharMap(final char[] arr) {
        this.arr = arr;
    }

    public static CharMap of(final char ... chars) {
        return new CharMap(chars);
    }
    public char character(int index) {
        return arr[index];
    }
    public int index(char character) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == character) {
                return i;
            }
        }
        throw new ArrayIndexOutOfBoundsException(String.format("Character %c is not present in charmap", character));
    }
    public int size() {
        return this.arr.length;
    }
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
