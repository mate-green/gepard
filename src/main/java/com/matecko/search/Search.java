package com.matecko.search;

import com.matecko.search.seq.Boundary;
import com.matecko.search.seq.Sequence;

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
       int lastSequencePosition = 0;
        for (int i = 0; i < count; i++) {
            final String last = binarySearch(_ids, sequence, lastSequencePosition);
            nextOnes.add(last);
            lastSequencePosition = sequence.toIndex(last) + 1;
            _ids.add(lastSequencePosition - 1, last);
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
        if (last < 0 || sequence.toIndex(ids.get(0)) != sequence.toIndex(boundary.floor())) {
            return sequence.toSequenceId(0);
        }
        String lastTried = ids.get(last);
        if (last == sequence.toIndex(lastTried)) {
            return sequence.toSequenceId(last + 1);
        }
        while (first <= last) {
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
    public int toPosition(String id) {
        final Sequence sequence = Sequence.within(boundary);
        return sequence.toIndex(id);
    }
}
