package seq;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoundaryTest {
    private Boundary boundary;

    @Test
    void givenAlphaSequenceReturnDistanceFromRootForEachChar() {
        boundary = new Boundary("ACA", "ZZZ");
        assertAll("all distances",
                () -> assertEquals(1, boundary.distanceFromRootChar('B', 'A')),
                () -> assertEquals(0, boundary.distanceFromRootChar('C', 'C')),
                () -> assertEquals(3, boundary.distanceFromRootChar('D', 'A'))
        );
    }
    @Test
    void givenNumSequenceReturnDistanceFromRootForEachChar() {
        boundary = new Boundary("123", "999");
        assertAll("all distances",
                () -> assertEquals(0, boundary.distanceFromRootChar('1', '1')),
                () -> assertEquals(3, boundary.distanceFromRootChar('5', '2')),
                () -> assertEquals(6, boundary.distanceFromRootChar('9', '3'))
        );
    }
    @Test
    void givenAlphaNumSequenceReturnDistanceFromRootForEachChar() {
        boundary = new Boundary("AAA", "999");
        assertAll("all distances",
                () -> assertEquals(0, boundary.distanceFromRootChar('A', 'A')),
                () -> assertEquals(25, boundary.distanceFromRootChar('Z', 'A')),
                () -> assertEquals(35, boundary.distanceFromRootChar('9', 'A'))
        );
    }
    @Test
    void givenDistanceAndIndexCalculateLetterForEachPosition() {
        boundary = new Boundary("123", "999");
        assertAll("letters from distance",
                () -> assertEquals('1', boundary.calculatedLetterFrom(0, '1')),
                () -> assertEquals('3', boundary.calculatedLetterFrom(1, '2')),
                () -> assertEquals('8', boundary.calculatedLetterFrom(5, '3'))
        );
    }
    @Test
    void givenBiggerDistanceFromFloorTharThanAllowedByCeilingThrowException() {
        boundary = new Boundary("987", "999");
        assertAll("",
                () -> assertThrows(IllegalArgumentException.class, () -> boundary.calculatedLetterFrom(1, '9')),
                () -> assertThrows(IllegalArgumentException.class, () -> boundary.calculatedLetterFrom(2, '8')),
                () -> assertThrows(IllegalArgumentException.class, () -> boundary.calculatedLetterFrom(3, '7'))
        );
    }
}