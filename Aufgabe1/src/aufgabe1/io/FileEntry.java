package aufgabe1.io;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by flbaue on 21.10.14.
 */
public class FileEntry {

    static final String NAME = "\\w+";
    static final String NODE = NAME;
    static final String EDGE = "(" + NAME + ")?";
    static final String DIRECTED = "\\x3D\\x3E";
    static final String UNDIRECTED = "\\x3D\\x3D";
    static final String NODE2 = "((" + UNDIRECTED + ")|(" + DIRECTED + ")" + NAME + ")?";
    static final String WEIGHT = "(:\\d+)?";
    static final String ENTRY = NODE + NODE2 + EDGE + WEIGHT + ";";
    static final Pattern ENTRY_PATTERN = Pattern.compile(ENTRY);

    private String node1Name;
    private String node2Name;
    private boolean isDirected;
    private String edgeName;
    private int weight;

    public FileEntry(String line) {
        Matcher matcher = ENTRY_PATTERN.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Line '" + line + "' is not valid");
        }
        parseLine(line);
    }

    private void parseLine(final String line) {
        if (line.contains(DIRECTED)) {
            isDirected = true;
        } else {
            isDirected = false;
        }

        Pattern node1Pattern = Pattern.compile(NODE);
        Matcher node1matcher = node1Pattern.matcher(line);
        node1matcher.find();
        node1Name = node1matcher.group();
        String lineEdited = line.substring(node1matcher.end());

        Pattern namePattern = Pattern.compile(NAME);

        Pattern node2Pattern = Pattern.compile(NODE2);
        Matcher node2Matcher = node2Pattern.matcher(lineEdited);
        if (node2Matcher.find()) {
            String node2Temp = node2Matcher.group();
            Matcher node2NameMatcher = namePattern.matcher(node2Temp);
            node2NameMatcher.find();
            node2Name = node2NameMatcher.group();
            lineEdited = lineEdited.substring(node2NameMatcher.end());
        }

        Matcher edgeNameMatcher = namePattern.matcher(lineEdited);
        if (edgeNameMatcher.find()) {
            edgeName = edgeNameMatcher.group();
            lineEdited = lineEdited.substring(edgeNameMatcher.end());
        }

        Pattern weightPattern = Pattern.compile(WEIGHT);
        Matcher weightMatcher = weightPattern.matcher(lineEdited);
        if (weightMatcher.find()) {
            String temp = weightMatcher.group().substring(lineEdited.indexOf(":") + 1);
            weight = Integer.parseInt(temp);
        }
    }

    public String getNode1Name() {
        return node1Name;
    }

    public String getNode2Name() {
        return node2Name;
    }

    public boolean isDirected() {
        return isDirected;
    }

    public String getEdgeName() {
        return edgeName;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        String result = getNode1Name();

        if (getNode2Name() != null) {
            if (isDirected()) {
                result += " -> ";
            } else {
                result += " -- ";
            }
            result += getNode2Name();
        }

        if (getEdgeName() != null) {
            result += getEdgeName();
        }

        if (getWeight() != 0) {
            result += ": " + getWeight();
        }

        result += ";";
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileEntry entry = (FileEntry) o;

        if (isDirected != entry.isDirected) return false;
        if (weight != entry.weight) return false;
        if (edgeName != null ? !edgeName.equals(entry.edgeName) : entry.edgeName != null) return false;
        if (node1Name != null ? !node1Name.equals(entry.node1Name) : entry.node1Name != null) return false;
        if (node2Name != null ? !node2Name.equals(entry.node2Name) : entry.node2Name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = node1Name != null ? node1Name.hashCode() : 0;
        result = 31 * result + (node2Name != null ? node2Name.hashCode() : 0);
        result = 31 * result + (isDirected ? 1 : 0);
        result = 31 * result + (edgeName != null ? edgeName.hashCode() : 0);
        result = 31 * result + weight;
        return result;
    }
}
