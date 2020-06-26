package seq;

public abstract class Sequence {
    public static class Fixed extends Sequence {
        private final Boundary boundary;
        public Fixed(Boundary boundary) {
            this.boundary = boundary;
        }

        @Override
        public String toSequenceId(final int index) {
            if (index == 0) return boundary.floor();
            StringBuilder sequence = new StringBuilder(Sequence.calculatePosition(index, boundary));
            int diff;
            while (boundary.ceiling().length() != sequence.length()) {
                diff = boundary.ceiling().length() - sequence.length();
                sequence.insert(0, boundary.floor().charAt(diff - 1));
            }
            return sequence.toString();
        }

        @Override
        public int toIndex(String sequenceId) {
            return calculateIndex(sequenceId, boundary);
        }
    }
    public static class Free extends Sequence {
        private final Boundary boundary;
        public Free(Boundary boundary) {
            this.boundary = boundary;
        }

        @Override
        public String toSequenceId(final int index) {
            if (index == 0) return boundary.floor();
            final String floor = lengthAdjusted(boundary.floor());
            return calculatePosition(index, new Boundary(floor, boundary.ceiling(), boundary.charMap()));
        }

        @Override
        public int toIndex(final String sequenceId) {
            final String sequence = lengthAdjusted(sequenceId);
            final String floor = lengthAdjusted(boundary.floor());
            return calculateIndex(sequence, new Boundary(floor, boundary.ceiling()));
        }
        private String lengthAdjusted(final String input) {
            StringBuilder adjusted = new StringBuilder(input);
            while (adjusted.length() != boundary.ceiling().length()) {
                adjusted.insert(0, boundary.charMap().getValue(0));
            }
            return adjusted.toString();
        }
    }
    public static Sequence within(final Boundary boundary) {
        return boundary.floor().length() != boundary.ceiling().length()
                ? new Free(boundary)
                : new Fixed(boundary);
    }
    public abstract String toSequenceId(final int index);
    public abstract int toIndex(final String sequenceId);

    private static String calculatePosition(final int index, final Boundary boundary) {
        final StringBuilder sb = new StringBuilder();
        char ch;
        int divisor, distance, log, charAt;
        int boundarySize = boundary.charMap().size();
        System.out.println(boundary.charMap().size());
        int accumulator = index;
        log = (int) Math.abs(Math.log(accumulator) / Math.log(boundarySize));
        System.out.println("log" + log);
        for (int i = log; i >= 0; i--){
            charAt = (boundary.floor().length() - 1) - i;
            System.out.println("char index" + charAt);
            divisor = (int) Math.pow(boundarySize, i);
            System.out.println("divisor " + divisor);
            distance = Math.abs(accumulator / divisor);
            System.out.println("distance " + distance);
            accumulator = accumulator - (distance * divisor);
            System.out.println("accumulator " + accumulator);
            ch = boundary.calculatedLetterFrom(distance, charAt);
            System.out.println(ch);
            sb.append(ch);
        }
        return sb.toString();
    }
    private static int calculateIndex(final String sequenceId, final Boundary boundary) {
        int boundarySize, charDistance;
        char current;
        int accumulator = 0;
        for (int i = 0; i < sequenceId.length(); i++) {
            current = sequenceId.charAt(i);
            boundarySize = boundary.charMap().size();
            if (boundary.charMap().getKey(current) == null)
                throw new IllegalArgumentException(String.format("character %c not found in character map", current));
            charDistance = boundary.distanceFromRootChar(current, i);
            accumulator += Math.pow(boundarySize, sequenceId.length() - (i + 1)) * charDistance;
        }
        return accumulator;
    }
}
