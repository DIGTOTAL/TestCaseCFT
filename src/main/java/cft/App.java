package cft;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        Config config = parseArgs(args);
        if (config.files.isEmpty()) {
            System.out.println("No input files specified. Reading from standard input.");
            return;
        } else {
            System.out.println("Input files: " + String.join(", ", config.files));
        }

        NumericStats intStats = new NumericStats();
        NumericStats floatStats = new NumericStats();
        StringStats stringStats = new StringStats();

        try (DataWriter writer = new DataWriter(config.outDir, config.prefix, config.append)) {
            LineHandler lineHandler = new LineHandler(writer, intStats, floatStats, stringStats);
            FileProcessor fileProcessor = new FileProcessor(lineHandler);

            for (String file : config.files) {
                fileProcessor.process(file);
            }
        } catch (IOException e) {
            System.err.println("Error initializing DataWriter: " + e.getMessage());
            return;
        }

        printStats(config, intStats, floatStats, stringStats);
    }

    private static Config parseArgs(String[] args) {
        Config config = new Config();
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o": config.outDir = args[++i]; break;
                case "-p": config.prefix = args[++i]; break;
                case "-a": config.append = true; break;
                case "-f": config.fullStats = true; break;
                case "-s": config.shortStats = true; break;
                default: config.files.add(args[i]);
            }
        }
        return config;
    }

    private static void printStats(Config config, NumericStats intStats, NumericStats floatStats, StringStats stringStats) {
        if (config.fullStats) {
            System.out.println("Full statistics:" +
                    "\n- Integers: " + intStats.getFullStats() +
                    "\n\n- Floats: " + floatStats.getFullStats() +
                    "\n\n- Strings: " + stringStats.getFullStats() + "\n");
        } else if (config.shortStats) {
            System.out.println("Short statistics:" +
                    "\n- Integers: " + intStats.getShortStats() +
                    "\n- Floats: " + floatStats.getShortStats() +
                    "\n- Strings: " + stringStats.getShortStats());
        }
    }
}