package seq;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoundaryTest {
    private Boundary boundary;

    @Test
    void givenAlphaSequenceReturnDistanceFromRootForEachChar() {
        boundary = new Boundary("ACA", "ZZZ");
        assertAll("all distances",
                () -> assertEquals(1, boundary.distanceFromRootChar('B', 0)),
                () -> assertEquals(0, boundary.distanceFromRootChar('C', 1)),
                () -> assertEquals(3, boundary.distanceFromRootChar('D', 2))
        );
    }
    @Test
    void givenNumSequenceReturnDistanceFromRootForEachChar() {
        boundary = new Boundary("123", "999");
        assertAll("all distances",
                () -> assertEquals(0, boundary.distanceFromRootChar('1', 0)),
                () -> assertEquals(3, boundary.distanceFromRootChar('5', 1)),
                () -> assertEquals(6, boundary.distanceFromRootChar('9', 2))
        );
    }
    @Test
    void givenAlphaNumSequenceReturnDistanceFromRootForEachChar() {
        boundary = new Boundary("AAA", "999");
        assertAll("all distances",
                () -> assertEquals(0, boundary.distanceFromRootChar('A', 0)),
                () -> assertEquals(25, boundary.distanceFromRootChar('Z', 1)),
                () -> assertEquals(35, boundary.distanceFromRootChar('9', 2))
        );
    }
    @Test
    void givenDistanceAndIndexCalculateLetterForEachPosition() {
        boundary = new Boundary("123", "999");
        assertAll("letters from distance",
                () -> assertEquals('1', boundary.calculatedLetterFrom(0, 0)),
                () -> assertEquals('3', boundary.calculatedLetterFrom(1, 1)),
                () -> assertEquals('8', boundary.calculatedLetterFrom(5, 2))
        );
    }
    @Test
    void givenBiggerDistanceFromFloorTharThanAllowedByCeilingThrowException() {
        boundary = new Boundary("987", "999");
        assertAll("",
                () -> assertThrows(IllegalArgumentException.class, () -> boundary.calculatedLetterFrom(1, 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> boundary.calculatedLetterFrom(2, 1)),
                () -> assertThrows(IllegalArgumentException.class, () -> boundary.calculatedLetterFrom(3, 2))
        );
    }
}