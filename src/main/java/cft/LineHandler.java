package cft;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Pattern;

public class LineHandler {
    private static final Pattern INT_PATTERN = Pattern.compile("[-+]?\\d+");
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("[-+]?((\\d+\\.\\d*)|(\\.\\d+)|(\\d+))(?:[eE][-+]?\\d+)?");

    private final DataWriter writer;
    private final NumericStats intStats;
    private final NumericStats floatStats;
    private final StringStats stringStats;

    public LineHandler(DataWriter writer, NumericStats intStats, NumericStats floatStats, StringStats stringStats) {
        this.writer = writer;
        this.intStats = intStats;
        this.floatStats = floatStats;
        this.stringStats = stringStats;
    }

    public void handle(String line) throws IOException {
        if (line.isEmpty()) return;
        if (INT_PATTERN.matcher(line).matches()) {
            writer.write(DataType.INTEGER, line);
            intStats.add(new BigInteger(line));
        } else if (DOUBLE_PATTERN.matcher(line).matches()) {
            writer.write(DataType.FLOAT, line);
            floatStats.add(new BigDecimal(line));
        } else {
            writer.write(DataType.STRING, line);
            stringStats.add(line);
        }
    }
}