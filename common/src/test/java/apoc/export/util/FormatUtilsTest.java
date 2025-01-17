package apoc.export.util;

import org.junit.AfterClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.test.rule.DbmsRule;
import org.neo4j.test.rule.ImpermanentDbmsRule;

import static org.junit.Assert.assertEquals;

/**
 * @author mh
 * @since 19.12.16
 */
public class FormatUtilsTest {

    @ClassRule
    public static DbmsRule db = new ImpermanentDbmsRule();

    @AfterClass
    public static void teardown() {
       db.shutdown();
    }

    @Test
    public void formatString() {
        assertEquals("\"\\n\"",FormatUtils.formatString("\n"));
        assertEquals("\"\\t\"",FormatUtils.formatString("\t"));
        assertEquals("\"\\\"\"",FormatUtils.formatString("\""));
        assertEquals("\"\\\\\"",FormatUtils.formatString("\\"));
        assertEquals("\"\\n\"",FormatUtils.formatString('\n'));
        assertEquals("\"\\t\"",FormatUtils.formatString('\t'));
        assertEquals("\"\\\"\"",FormatUtils.formatString('"'));
        assertEquals("\"\\\\\"",FormatUtils.formatString('\\'));
    }

    @Test
    public void joinLabels() {
        final String delimiter = ":";
        try (Transaction tx = db.beginTx()) {
            Node node = tx.createNode();
            assertEquals("", FormatUtils.joinLabels(node, delimiter));

            node.addLabel(Label.label("label_a"));
            node.addLabel(Label.label("label_c"));
            node.addLabel(Label.label("label_b"));
            assertEquals("label_a:label_b:label_c", FormatUtils.joinLabels(node, delimiter));
        }
    }

}
