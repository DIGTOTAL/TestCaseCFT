package cft;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class NumericStats {
    private int count = 0;
    private BigDecimal min = null, max = null, sum = BigDecimal.ZERO;

    public void add(BigDecimal value) {
        count++;
        sum = sum.add(value);
        min = (min == null) ? value : min.min(value);
        max = (max == null) ? value : max.max(value);
    }

    public void add(BigInteger value) {
        add(new BigDecimal(value));
    }

    public BigDecimal getAverage() {
        return count > 0 ? sum.divide(new BigDecimal(count), 10, RoundingMode.HALF_DOWN) : BigDecimal.ZERO;
    }

    public String getShortStats() {
        return "\n count: " + count;
    }

    public String getFullStats() {
        return String.format("\n count: %d, \n min: %s, \n max: %s, \n sum: %.2f, \n avg: %.2f",
                count, min, max, sum, getAverage());
    }
}