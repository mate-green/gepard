package com.matecko.search;

import com.matecko.search.seq.Boundary;
import com.matecko.search.seq.CharMap;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SearchTest {
    private final Boundary boundary = new Boundary("0", "999");
    private final Search search = new Search(boundary);

    @Test
    void addLastToList() {
        final List<String> seqs = Stream.iterate(0, n -> n + 1)
                .limit(10)
                .map(String::valueOf)
                .collect(Collectors.toList());
        assertEquals("10", search.nextOne(seqs));
    }
    @Test
    void fiveIsMissing() {
        final List<String> seqs = Stream.iterate(0, n -> n + 1)
                .limit(10)
                .filter(n -> n != 5)
                .map(String::valueOf)
                .collect(Collectors.toList());
        assertEquals("5", search.nextOne(seqs));
    }

    @Test
    void oneBeforeLastIsMissing() {
        final List<String> seqs = Stream.iterate(0, n -> n + 1)
                .filter(i -> i != 998)
                .limit(999)
                .map(String::valueOf)
                .collect(Collectors.toList());
        assertEquals("998", search.nextOne(seqs));
    }
    @Test
    void oneBeforeLastIsMissingAlpha() {
        final Boundary b = new Boundary("A", "ZZZ");
        final Search s = new Search(b);
        final List<String> seqs = IntStream.iterate('A', c -> c + 1)
                .limit(25)
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.toList());
        assertEquals("Z", s.nextOne(seqs));
    }
    @Test
    void oneBeforeLastIsMissingWhenBoundaryPartial() {
        final Boundary b = new Boundary("AA", "JJ", CharMap.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'));
        final Search s = new Search(b);
        final List<String> seqs = IntStream.iterate('A', c -> c + 1)
                .limit(10)
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.toList());
        assertEquals("BA", s.nextOne(seqs));
    }
    @Test
    void ceilingUsesCharThatIsNotPresentInCharMapThenThrowsException() {
        final Boundary b = new Boundary("A", "JZ", CharMap.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'));
        final Search s = new Search(b);
        final List<String> seqs = IntStream.iterate('A', c -> c + 1)
                .limit(10)
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.toList());
        assertThrows(IllegalArgumentException.class, () -> s.nextOne(seqs));
    }

    @Test
    void firstSequenceIsMissing() {
        final List<String> seqs = List.of("7");
        assertEquals("0", search.nextOne(seqs));
    }
    @Test
    void firstSequenceIsMissingWithEmptyList() {
        final List<String> seqs = List.of();
        assertEquals("0", search.nextOne(seqs));
    }
    @Test
    void addLastToListAlpha() {
        Boundary b = new Boundary("A", "ZZZ");
        Search s = new Search(b);
        final List<String> seqs = IntStream.iterate('A', c -> c + 1)
                .limit(10)
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.toList());
        assertEquals("K", s.nextOne(seqs));
    }
    @Test
    void whenOneIsMissingInTheRowAndTailIsMissingReturnAllValid_Fixed() {
        final Boundary b = new Boundary("0000", "9999");
        final Search s = new Search(b);
        final List<String> seq = Stream.iterate(0, n -> n + 1)
                .limit(1550)
                .filter(n -> n != 1485)
                .filter(n -> n < 1500)
                .map(String::valueOf)
                .map(inp -> "0".repeat(b.floor().length() - inp.length()).concat(inp))
                .collect(Collectors.toList());
        List<String> expected = List.of("1485", "1500", "1501", "1502");
        List<String> actual = s.nextOnes(seq, 4);
        assertEquals(expected, actual);
    }
    @Test
    void whenOneIsMissingInTheRowAndTailIsMissingReturnAllValid_Free() {
        final Boundary b = new Boundary("0", "9999");
        final Search s = new Search(b);
        final List<String> seq = Stream.iterate(0, n -> n + 1)
                .limit(1550)
                .filter(n -> n != 1485)
                .filter(n -> n < 1500)
                .map(String::valueOf)
                .collect(Collectors.toList());
        List<String> expected = List.of("1485", "1500", "1501", "1502");
        List<String> actual = s.nextOnes(seq, 4);
        assertEquals(expected, actual);
    }
    @Test
    void whenEveryFiveMissingIn100ShouldReturnFirst4Multiples() {
        Boundary b = new Boundary("0", "999");
        Search s = new Search(b);
        final List<String> seqs = Stream.iterate(0, n -> n + 1)
                .limit(100)
                .filter(n -> n % 5 != 0)
                .map(String::valueOf)
                .collect(Collectors.toList());
        List<String> expected = List.of("0", "5", "10", "15");
        assertEquals(expected, s.nextOnes(seqs, 4));
    }
    @Test
    void whenSequenceIsFullThenThrowException() {
        Search s = new Search(new Boundary("0", "10"));
        final List<String> seq = Stream.iterate(0, n -> n + 1)
                .limit(11)
                .map(String::valueOf)
                .collect(Collectors.toList());
        assertThrows(IllegalStateException.class, () -> s.nextOne(seq));
    }
}