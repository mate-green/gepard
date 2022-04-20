package com.matecko.search.seq;

/**
 * A sequence
 */
public abstract class Sequence {
    /**
     * A sequence with fixed length.
     * <p>
     *     <strong>Example: </strong> 000, 001, 002 ... 100, 101
     * </p>
     */
    protected static class Fixed extends Sequence {
        private final Boundary boundary;
        public Fixed(Boundary boundary) {
            this.boundary = boundary;
        }

        @Override
        public String toSequenceId(final int index) {
            if (index == 0) return boundary.floor();
            return lengthAdjusted(
                    Sequence.calculatedSequence(index, boundary)
            );
        }

        @Override
        public int toIndex(String sequenceId) {
            return calculatedIndex(sequenceId, boundary);
        }

        @Override
        public boolean isFixed() {
            return true;
        }

        public String lengthAdjusted(final String input) {
            StringBuilder adjusted = new StringBuilder(input);
            while (adjusted.length() != boundary.ceiling().length()) {
                adjusted.insert(0, boundary.charMap().character(0));
            }
            return adjusted.toString();
        }
    }
    /**
     * A sequence with free length.
     * <p>
     *     <strong>Example: </strong> 0, 1, 2, ... 100, 101
     * </p>
     */
    protected static class Free extends Sequence {
        private final Boundary boundary;
        public Free(Boundary boundary) {
            this.boundary = boundary;
        }

        @Override
        public String toSequenceId(final int index) {
            if (index == 0) return boundary.floor();
            return calculatedSequence(index, boundary);
        }

        @Override
        public int toIndex(final String sequenceId) {
            return calculatedIndex(sequenceId, boundary);
        }

        @Override
        public boolean isFixed() {
            return false;
        }
    }

    /**
     * Constructs either {@link Fixed} or {@link Free} sequence instance
     * @param boundary The sequence boundaries
     * @return new Sequence instance
     * @throws IllegalArgumentException if boundary contains character that is not part of CharMap
     */
    public static Sequence within(final Boundary boundary) {
        if (!boundary.isPartOfCharMap())
            throw new IllegalArgumentException("Boundary is not within the CharMap");
        return boundary.floor().length() != boundary.ceiling().length()
                ? new Free(boundary)
                : new Fixed(boundary);
    }

    /**
     * Generates sequence based on its index
     * @param index distance index from floor boundary
     * @return string representation of sequence
     */
    public abstract String toSequenceId(final int index);

    /**
     * Calculates distance index from floor boundary
     * @param sequenceId sequence
     * @return distance index of sequence
     */
    public abstract int toIndex(final String sequenceId);

    /**
     * Returns if this instance is a Fixed
     * @return true, if this is a Fixed, false otherwise
     */
    public abstract boolean isFixed();

    private static String calculatedSequence(final int position, final Boundary boundary) {
        char ch;
        int accumulator, divisor, distance;

        final StringBuilder sb = new StringBuilder();
        final int floorPosition = calculatedIndex(boundary.floor(), Boundary.withRootFloor(boundary));

        accumulator = position + floorPosition;

        final int boundarySize = boundary.charMap().size();
        final int log = (int) Math.abs(Math.log(accumulator) / Math.log(boundarySize));
        for (int i = log; i >= 0; i--) {
            divisor = (int) Math.pow(boundarySize, i);
            distance = Math.abs(accumulator / divisor);
            accumulator = accumulator - (distance * divisor);
            ch = boundary.calculatedCharFrom(distance);
            sb.append(ch);
        }
        return sb.toString();
    }
    private static boolean lengthExceedsCeiling(final String input, final String ceiling) {
        return input.length() > ceiling.length();
    }

    /**
     * Calculates the index of sequence for boundary defined
     * @param sequenceId sequence
     * @param boundary boundary defined
     * @return calculated index
     */
    public static int calculatedIndex(final String sequenceId, final Boundary boundary) {
        if (lengthExceedsCeiling(sequenceId, boundary.ceiling()))
            throw new IllegalArgumentException("Input length exceeds ceiling length");

        final int difference = boundary.ceiling().length() - boundary.floor().length();
        int boundarySize, charDistance;
        char current, root;
        int accumulator = 0;
        for (int i = 0; i < sequenceId.length(); i++) {
            root = difference > i
                    ? boundary.charMap().character(0)
                    : boundary.floor().charAt(Math.abs(difference - i));
            current = sequenceId.charAt(i);
            boundarySize = boundary.charMap().size();
            charDistance = boundary.distanceFromRootChar(current, root);
            accumulator += Math.pow(boundarySize, sequenceId.length() - (i + 1)) * charDistance;
        }
        return accumulator;
    }
}
