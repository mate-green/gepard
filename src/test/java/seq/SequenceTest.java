package seq;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SequenceTest {

    private Sequence sequence;

    @Test
    void whenSeqIndexWithinFixedBoundariesIsZeroThenReturnFloor() {
        sequence = Sequence.within(new Boundary("000", "999"));
        assertEquals("000", sequence.toSequenceId(0));
        //assertEquals(0, sequence.toIndex("000"));
    }
    @Test
    void whenSeqIndexWithinFreeBoundariesIsZeroThenReturnFloor() {
        sequence = Sequence.within(new Boundary("A", "ZZZ"));
        assertEquals("A", sequence.toSequenceId(0));
    }
    @Test
    void whenSeqIndexWithinFreeBoundariesIs10Return9() {
        sequence = Sequence.within(new Boundary("0", "999"));
        assertEquals("10", sequence.toSequenceId(10));
    }
    @Test
    void whenSeqIndexWithinFixedBoundariesIs26ReturnABA() {
        sequence = Sequence.within(new Boundary("AAA", "ZZZ"));
        assertEquals("ABA", sequence.toSequenceId(26));
    }
    @Test
    void whenSeqIndexIs7WithinFixedBoundariesWithCustomCharMapOf0And1Return111() {
        sequence = Sequence.within(new Boundary("000", "111", CharMap.of('0', '1')));
        assertEquals("111", sequence.toSequenceId(7));
    }
    @Test
    void whenSeqIndexIs7WithinFreeBoundariesWithCustomCharMapOf0And1Return111() {
        sequence = Sequence.within(new Boundary("000", "111", CharMap.of('0', '1')));
        assertEquals("111", sequence.toSequenceId(7));
    }
    @Test
    void whenSeqIndexIs10WithinFixedBoundariesWithFloorOf111Return121() {
        sequence = Sequence.within(new Boundary("111", "999"));
        assertEquals("121", sequence.toSequenceId(10));
    }
    @Test
    void whenSeqIndexIsHigherThanCeilingIndexThrowException() {
        sequence = Sequence.within(new Boundary("000", "030"));
        assertThrows(IllegalArgumentException.class, () -> sequence.toSequenceId(31));
    }
    //TODO test above with custom charsets
    @Test
    void whenSequenceIdContainsCharOutsideOfCharMapThrowException() {
        sequence = Sequence.within(new Boundary("000", "999"));
        assertThrows(IllegalArgumentException.class, () -> sequence.toIndex("AAZ"));
    }
    @Test
    void whenSequenceIdIsBAAReturn676() {
        sequence = Sequence.within(new Boundary("AAA", "ZZZ"));
        assertEquals(676, sequence.toIndex("BAA"));
    }
    @Test
    void test() {
        sequence = Sequence.within(new Boundary("0", "999"));
        assertEquals(10, sequence.toIndex("10"));
    }
}