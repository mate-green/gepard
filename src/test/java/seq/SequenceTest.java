package seq;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SequenceTest {

    private Sequence sequence;

    @Test
    void whenSeqIndexWithinFixedBoundariesIsZeroThenReturnFloor() {
        sequence = Sequence.within(new Boundary("000", "999"));
        assertEquals("000", sequence.toSequenceId(0));
    }
    @Test
    void whenSeqIndexWithinFreeBoundariesIsZeroThenReturnFloor() {
        sequence = Sequence.within(new Boundary("A", "ZZZ"));
        assertEquals("A", sequence.toSequenceId(0));
    }
    @Test
    void whenSeqIndexWithinFreeBoundariesIs10Return10() {
        sequence = Sequence.within(new Boundary("0", "999"));
        assertEquals("10", sequence.toSequenceId(10));
    }
    @Test
    void whenSeqIndexWithinFixedBoundariesIs27ReturnABB() {
        sequence = Sequence.within(new Boundary("AAA", "ZZZ"));
        assertEquals("ABB", sequence.toSequenceId(27));
    }
    @Test
    void whenSeqIndexIs8WithinFixedBoundariesWithCustomCharMapOf0And1Return111() {
        sequence = Sequence.within(new Boundary("000", "111", CharMap.of('0', '1')));
        assertEquals("111", sequence.toSequenceId(7));
    }
    @Test
    void whenPositionHigherThanCeilingThrowException() {
        sequence = Sequence.within(new Boundary("00", "10"));
        assertThrows(IllegalArgumentException.class, () -> sequence.toSequenceId(12));
    }
    @Test
    void whenSeqIndexIs10WithinFixedBoundariesWithFloorOf129Return208() {
        sequence = Sequence.within(new Boundary("129", "999"));
        assertEquals("208", sequence.toSequenceId(79));
    }
    @Test
    void whenSequenceIdIsBAAReturn676() {
        sequence = Sequence.within(new Boundary("A", "ZZZ"));
        assertEquals(676, sequence.toIndex("BAA"));
    }
    @Test
    void whenFloorAndCeilingLengthIsEqualThenReturnFixedSequence() {
        sequence = Sequence.within(new Boundary("AAA", "BBB"));
        assertTrue(sequence.isFixed());
    }
    @Test
    void whenFloorAndCeilingLengthDifferentThenReturnFreeSequence() {
        sequence = Sequence.within(new Boundary("A", "BBB"));
        assertFalse(sequence.isFixed());
    }
}