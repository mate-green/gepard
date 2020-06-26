package search;

import org.junit.jupiter.api.Test;
import seq.Boundary;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {
    private final Boundary boundary = new Boundary("0", "999");
    private final Search search = new Search(boundary);

    @Test
    void addLastToList() {
        final List<String> seqs = Stream.iterate(0, n -> n + 1)
                .limit(10)
                .map(i -> String.valueOf(i))
                .collect(Collectors.toList());
        assertEquals("10", search.free(seqs));
    }
    @Test
    void oneBeforeLastIsMisisng() {
        final List<String> seqs = Stream.iterate(0, n -> n + 1)
                .filter(i -> i != 998)
                .limit(999)
                .map(i -> String.valueOf(i))
                .collect(Collectors.toList());
        assertEquals("998", search.free(seqs));
    }
    @Test
    void firstSequenceIsMissing() {
        final List<String> seqs = Arrays.asList("7");
        assertEquals("0", search.free(seqs));
    }
    @Test
    void firstSequenceIsMissingWithEmptyList() {
        final List<String> seqs = Arrays.asList();
        assertEquals("0", search.free(seqs));
    }
    @Test
    void addLastToListAlpha() {
        Boundary b = new Boundary("A", "ZZZ");
        Search s = new Search(b);
        final List<String> seqs = IntStream.iterate('A', c -> c + 1)
                .limit(10)
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.toList());
        assertEquals("K", s.free(seqs));
    }
}