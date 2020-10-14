package search;

import seq.Boundary;
import seq.Sequence;

import java.util.List;
public final class Search {
    private final Boundary boundary;
    public Search(Boundary boundary) {
        this.boundary = boundary;
    }

    public String free(List<String> ids) {
        final Sequence sequence = Sequence.within(boundary);
        return binarySearch(ids, sequence);
    }

    private String binarySearch(List<String> ids, Sequence sequence) {
        int first = 0;
        int last = ids.size() - 1;
        int middle = (first + last) / 2;
        if (last < 0 || first != sequence.toIndex(ids.get(0))) {
            return sequence.toSequenceId(0);
        }
        String lastTried = ids.get(last);
        if (last == sequence.toIndex(lastTried)) {
            return sequence.toSequenceId(last + 1);
        }
        while (first <= last) {
//            System.out.println("firstVal: " + first);
//            System.out.println("middleVal: " + middle);
//            System.out.println("lastVal: " + last);
//            System.out.println("List: " + lenghtAdjusted(ids.get(middle)));
//            System.out.println("Real: " + sequence.toSequenceId(middle));
            if (lenghtAdjusted(ids.get(middle)).equals(sequence.toSequenceId(middle))) {
                first = middle + 1;
            } else {
                if (lenghtAdjusted(ids.get(middle - 1)).equals(sequence.toSequenceId(middle - 1))) {
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
        StringBuilder adjusted = new StringBuilder(input);
        while (adjusted.length() != boundary.ceiling().length()) {
            adjusted.insert(0, boundary.charMap().character(0));
        }
        return adjusted.toString();
    }
//    private String _free(Sequence s, int size, String lastTried) {
//        if (size != s.toPosition(lastTried)) {
//            return s.toSequenceId(size + 1);
//        }
//        return _free(s, size/2, )
//    }
}
