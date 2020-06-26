package seq;

import java.util.HashMap;

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
    private final HashMap<Integer,Character> map;
    private final HashMap<Character,Integer> invertedMap;

    private CharMap(HashMap<Integer, Character> map, HashMap<Character, Integer> invertedMap) {
        this.map = map;
        this.invertedMap = invertedMap;
    }

    public static CharMap of(char ... chars) {
        HashMap<Integer,Character> map = new HashMap<>();
        HashMap<Character,Integer> invertedMap = new HashMap<>();
        for (int i = 0; i < chars.length; i++) {
            map.put(i, chars[i]);
            invertedMap.put(chars[i], i);
        }
        return new CharMap(map, invertedMap);
    }
    public Character getValue(Integer integer) {
        return map.get(integer);
    }
    public Integer getKey(Character character) {
        return invertedMap.get(character);
    }
    public int size() {
        return this.map.size();
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
