package search;

import org.junit.jupiter.api.Test;
import seq.Boundary;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void addMultiple() {
        final List<String> seqs = Stream.iterate(0, n -> n + 1)
                .limit(100)
                .filter(n -> n % 5 != 0)
                .map(n -> String.valueOf(n))
                .collect(Collectors.toList());
        List<String> expected = List.of("0", "5", "10", "15");
        List<String> actual = search.nextOnes(seqs, 4);
        assertEquals(expected, actual);
    }
    @Test
    void sequenceToPosition() {
        final int position = search.toPosition("5");
        assertEquals(5, position);
    }
}