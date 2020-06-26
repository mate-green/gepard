package seq;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharMapTest {

    @Test
    void givenCharVarargsGetKeys() {
        CharMap map = CharMap.of('G', 'A', 'P');
        assertAll("charmap keys",
                () -> assertEquals(0, map.getKey('G')),
                () -> assertEquals(1, map.getKey('A')),
                () -> assertEquals(2, map.getKey('P'))
        );
    }
    @Test
    void givenCharVarargsGetVals() {
        CharMap map = CharMap.of('G', 'A', 'P');
        assertAll("charmap values",
                () -> assertEquals('G', map.getValue(0)),
                () -> assertEquals('A', map.getValue(1)),
                () -> assertEquals('P', map.getValue(2))
        );
    }
    @Test
    void givenCharMapReturnSize() {
        CharMap map = CharMap.of('G', 'A', 'P');
        assertEquals(3, map.size());
    }
    @Test
    void givenNumFloorAndCeilingReturnDefaultNumCharMap() {
        CharMap expected = CharMap.NUM;
        CharMap actual = CharMap.defaulted("000", "123");
        assertEquals(expected, actual);
    }
    @Test
    void givenAlphaFloorAndCeilingReturnDefaultAlphaCharMap() {
        CharMap expected = CharMap.ALPHA;
        CharMap actual = CharMap.defaulted("ABZ", "ZQP");
        assertEquals(expected, actual);
    }
    @Test
    void givenAlphaNumFloorAndCeilingReturnDefaultAlphaNumCharMap() {
        CharMap expected = CharMap.ALPHA_FIRST_THEN_NUM;
        CharMap actual = CharMap.defaulted("AAA", "999");
        assertEquals(expected, actual);
    }
    @Test
    void givenNumAlphaFloorAndCeilingReturnDefaultNumAlphaCharMap() {
        CharMap expected = CharMap.NUM_FIRST_THEN_ALPHA;
        CharMap actual = CharMap.defaulted("00A", "ZZZ");
        assertEquals(expected, actual);
    }
}