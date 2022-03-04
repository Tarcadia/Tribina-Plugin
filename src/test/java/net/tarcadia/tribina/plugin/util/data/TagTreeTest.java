package net.tarcadia.tribina.plugin.util.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagTreeTest {

    @Test
    void doTest() {
        TagTree<Boolean> tt = new TagTree<>();
        tt.put("a.b.c", true);
        tt.put("a.b.e", true);
        tt.put("a.b.d", false);
        tt.put("a.a", false);
        tt.put("b.a", true);
        System.out.println(tt.get("a.b"));
        System.out.println(tt.get(".a..b..c"));
        System.out.println(tt.get("a.b.c"));
        System.out.println(tt.get("a.b.e"));
        System.out.println(tt.get("a.d.b"));
        System.out.println(tt.get("a.d.a"));
        System.out.println(tt.get("a.a"));
        System.out.println(tt.get("b.a"));
        System.out.println(tt.getTags(true));

        var branch = tt.popBranch("a.b");
        System.out.println(tt.get("a.b"));
        System.out.println(tt.get(".a..b..c"));
        System.out.println(tt.get("a.b.c"));
        System.out.println(tt.get("a.b.e"));
        System.out.println(tt.get("a.d.b"));
        System.out.println(tt.get("a.d.a"));
        System.out.println(tt.get("a.a"));
        System.out.println(tt.get("b.a"));
        System.out.println(branch.getId());
        System.out.println(branch.getPath());
        System.out.println(branch.getTags(true));
        System.out.println(tt.getTags(true));

    }
}