package cft;

public class StringStats {
    private int count = 0;
    private int minLength = Integer.MAX_VALUE, maxLength = Integer.MIN_VALUE;

    public void add(String value) {
        count++;
        int length = value.length();
        minLength = Math.min(minLength, length);
        maxLength = Math.max(maxLength, length);
    }

    public String getShortStats() {
        return "\n count: " + count;
    }

    public String getFullStats() {
        return String.format("\n count: %d, \n minLength: %s, \n maxLength: %s",
                count, minLength == Integer.MAX_VALUE ? "-" : minLength,
                maxLength == Integer.MIN_VALUE ? "-" : maxLength);
    }
}