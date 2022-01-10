package search;

import seq.Boundary;
import seq.Sequence;

import java.util.ArrayList;
import java.util.List;
public final class Search {
    private final Boundary boundary;
    public Search(Boundary boundary) {
        this.boundary = boundary;
    }

    public String nextOne(final List<String> ids) {
        final Sequence sequence = Sequence.within(boundary);
        return binarySearch(ids, sequence);
    }
    public List<String> nextOnes(final List<String> ids, final int count) {
       final Sequence sequence = Sequence.within(boundary);
       List<String> nextOnes = new ArrayList<>();
       List<String> _ids = new ArrayList<>(ids);
       int lastSequencePostion = 0;
        for (int i = 0; i < count; i++) {
            //System.out.println(_ids);
            final String last = binarySearch(_ids, sequence, lastSequencePostion);
            nextOnes.add(last);
            _ids.add(lastSequencePostion, last);
            lastSequencePostion = sequence.toIndex(last) + 1;
        }
       return nextOnes;
    }
    private String binarySearch(final List<String> ids, final Sequence sequence) {
        return binarySearch(ids, sequence, 0);
    }
    private String binarySearch(final List<String> ids, final Sequence sequence, final int _first) {
        int first = _first;
        int last = ids.size() - 1;
        int middle = (first + last) / 2;
        System.out.println("firstVal: " + first);
        System.out.println("middleVal: " + middle);
        System.out.println("lastVal: " + last);
        if (last < 0 || sequence.toIndex(ids.get(0)) != sequence.toIndex(boundary.floor())) {
            return sequence.toSequenceId(0);
        }
        String lastTried = ids.get(last);
        if (last == sequence.toIndex(lastTried)) {
            return sequence.toSequenceId(last + 1);
        }
        while (first <= last) {
            //System.out.println("List: " + ids.get(middle));
            //System.out.println("Real: " + sequence.toSequenceId(middle));
            if (ids.get(middle).equals(sequence.toSequenceId(middle))) {
                first = middle + 1;
            } else {
                if (ids.get(middle - 1).equals(sequence.toSequenceId(middle - 1))) {
                    lastTried = sequence.toSequenceId(middle);
                    break;
                } else {
                    last = middle - 1;
                }
            }
            middle = (first + last) / 2;
        }
        return lastTried;
    }
    private String lenghtAdjusted(final String input) {
        final StringBuilder adjusted = new StringBuilder(input);
        while (adjusted.length() != boundary.ceiling().length()) {
            adjusted.insert(0, boundary.charMap().character(0));
        }
        return adjusted.toString();
    }
    public int toPosition(String id) {
        final Sequence sequence = Sequence.within(boundary);
        return sequence.toIndex(id);
    }
}
