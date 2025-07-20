package cft;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DataWriter implements AutoCloseable {
    private final Map<DataType, BufferedWriter> writers = new HashMap<>();
    private final Map<DataType, String> fileNames = new HashMap<>();
    private final boolean append;

    public DataWriter(String dir, String prefix, boolean append) throws IOException {
        this.append = append;
        File outDir = new File(dir);
        if (!outDir.exists() && !outDir.mkdirs()) {
            throw new IOException("Failed to create output directory: " + dir);
        }
        fileNames.put(DataType.INTEGER, dir + File.separator + prefix + "integers.txt");
        fileNames.put(DataType.FLOAT, dir + File.separator + prefix + "floats.txt");
        fileNames.put(DataType.STRING, dir + File.separator + prefix + "strings.txt");
    }

    public void write(DataType type, String value) throws IOException {
        if (!writers.containsKey(type)) {
            writers.put(type, new BufferedWriter(new FileWriter(fileNames.get(type), append)));
        }
        BufferedWriter writer = writers.get(type);
        writer.write(value);
        writer.newLine();
    }

    @Override
    public void close() throws IOException {
        for (BufferedWriter w : writers.values()) {
            w.flush();
            w.close();
        }
    }
}
