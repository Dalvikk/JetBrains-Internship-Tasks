import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TOCParser {
    private final List<String> file;
    private final static Pattern headerPattern = Pattern.compile("^ {0,3}(#{1,6}) +(.+)");

    public TOCParser(List<String> file) {
        this.file = file;
    }

    public List<String> parse() {
        return parseTOC();
    }

    private List<String> parseTOC() {
        List<Header> mainHeaders = parseRoots();
        processUniqueHeaders(mainHeaders);
        List<String> result = new ArrayList<>();
        for (int i = 0; i < mainHeaders.size(); i++) {
            parseTOCTree(mainHeaders.get(i), 0, i + 1, result);
        }
        return result;
    }

    private List<Header> parseRoots() {
        List<Header> mainHeaders = new ArrayList<>();
        Stack<Header> stack = new Stack<>(); // nested headers before with level <= current.level
        for (String line : file) {
            Header header = getHeader(line);
            if (header == null) continue;
            while (!stack.empty() && header.level <= stack.peek().level) {
                stack.pop();
            }
            if (stack.empty()) {
                mainHeaders.add(header);
            } else {
                stack.peek().addChildren(header);
            }
            stack.push(header);
        }
        return mainHeaders;
    }

    private void processUniqueHeaders(List<Header> headers) {
        Map<String, Integer> count = new HashMap<>();
        for (Header header : headers) {
            walkChildren(header, count);
        }
    }

    private void walkChildren(Header header, Map<String, Integer> count) {
        String link = header.toHyperlink();
        count.merge(link, 1, Integer::sum);
        header.setUniqueIndex(count.get(link));
        for (Header child : header.getChildren()) {
            walkChildren(child, count);
        }
    }

    private void parseTOCTree(Header header, int depth, int index, List<String> result) {
        String unique = (header.getUniqueIndex() == 1 ? "" : "-" + (header.getUniqueIndex() - 1));
        result.add("\t".repeat(depth) + String.format("%d. [%s](#%s%s)  ", index, header.name, header.toHyperlink(), unique));
        for (int i = 0; i < header.getChildren().size(); i++) {
            parseTOCTree(header.getChildren().get(i), depth + 1, i + 1, result);
        }
    }

    private Header getHeader(String line) {
        Matcher matcher = headerPattern.matcher(line);
        if (matcher.find()) {
            return new Header(matcher.group(1).length(), matcher.group(2));
        }
        return null;
    }
}
