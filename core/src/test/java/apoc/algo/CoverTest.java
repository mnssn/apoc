package apoc.algo;

import apoc.util.TestUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.neo4j.test.rule.DbmsRule;
import org.neo4j.test.rule.ImpermanentDbmsRule;

import static org.junit.Assert.assertEquals;

/**
 * @author mh
 * @since 21.05.16
 */
public class CoverTest {

    @ClassRule
    public static DbmsRule db = new ImpermanentDbmsRule();

    @BeforeClass
    public static void setUp() {
        TestUtil.registerProcedure(db, Cover.class);
        db.executeTransactionally("CREATE (a)-[:X]->(b)-[:X]->(c)-[:X]->(d)");
    }

    @AfterClass
    public static void teardown() {
       db.shutdown();
    }

    @Test
    public void testCover() {
        TestUtil.testCall(db,
                "match (n) with collect(id(n)) as nodes call apoc.algo.cover(nodes) yield rel return count(*) as c",
                (r) -> assertEquals(3L,r.get("c")));
    }
}
