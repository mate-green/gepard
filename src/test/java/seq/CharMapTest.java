package seq;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharMapTest {

    @Test
    void givenCharVarargsGetKeys() {
        CharMap map = CharMap.of('G', 'A', 'P');
        assertAll("charmap keys",
                () -> assertEquals(0, map.index('G')),
                () -> assertEquals(1, map.index('A')),
                () -> assertEquals(2, map.index('P'))
        );
    }
    @Test
    void givenCharVarargsGetVals() {
        CharMap map = CharMap.of('G', 'A', 'P');
        assertAll("charmap values",
                () -> assertEquals('G', map.character(0)),
                () -> assertEquals('A', map.character(1)),
                () -> assertEquals('P', map.character(2))
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
    @Test
    void givenCharOutOfCharMapThrowException() {
        CharMap map = CharMap.of('A', 'B');
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> map.index('D'));
    }
}