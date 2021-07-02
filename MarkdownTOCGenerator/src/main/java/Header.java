import java.util.ArrayList;
import java.util.List;

public class Header {
    public final int level;
    public final String name;
    private int uniqueIndex = 1; // for headers with the same name
    private final List<Header> children = new ArrayList<>();

    public Header(int level, String name) {
        this.level = level;
        this.name = name;
    }

    public String toHyperlink() {
        return normalize(name.trim().toLowerCase().chars()
                .map((ch) -> Character.isLetterOrDigit(ch) ? ch : '-')
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString());
    }

    private String normalize(String str) {
        return str.replaceAll("-+", "-").replaceAll("^-", "").replaceAll("-$", "");
    }

    public void addChildren(Header header) {
        children.add(header);
    }

    public List<Header> getChildren() {
        return children;
    }

    public int getUniqueIndex() {
        return uniqueIndex;
    }

    public void setUniqueIndex(int uniqueIndex) {
        this.uniqueIndex = uniqueIndex;
    }
}
