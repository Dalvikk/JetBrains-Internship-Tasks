import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: <path to input file>");
            return;
        }
        // Flags
        boolean reWrite = Arrays.asList(args).contains("--rewrite");
        boolean onlyTOC = Arrays.asList(args).contains("--onlyTOC");
        try {
            List<String> file = Files.readAllLines(Path.of(args[0]));
            try (OutputStream out = reWrite ? new FileOutputStream(args[0]) : System.out) {
                TOCParser parser = new TOCParser(file);
                List<String> TOC = parser.parse();
                if (!onlyTOC) {
                    TOC.add(System.lineSeparator());
                    TOC.addAll(file);
                }
                write(out, TOC);
            }
        } catch (IOException e) {
            System.out.println(e.getClass() + " happened with the message: " + e.getMessage());
        }
    }

    private static void write(OutputStream out, List<String> file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
        writer.write(String.join(System.lineSeparator(), file));
        writer.newLine();
        writer.flush();
    }
}
