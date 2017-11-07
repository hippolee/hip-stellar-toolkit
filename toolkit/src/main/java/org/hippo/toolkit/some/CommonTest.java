package org.hippo.toolkit.some;

/**
 * Created by litengfei on 2017/9/6.
 */
public class CommonTest {

    private static void testString() {
        String a = "china";
        String b = "china";
        String c = b;
        String d = new String("china");
        String e = new String("china");
        String f = e;

        System.out.println(a == b);
        System.out.println(b == c);
        System.out.println(c == d);
        System.out.println(d == e);
        System.out.println(e == f);
    }

    private static void testInteger() {
        Integer a = 7;
        Integer b = 7;
        Integer c = b;
        Integer d = new Integer(7);
        Integer e = new Integer(7);
        Integer f = e;

        System.out.println(a == b);
        System.out.println(b == c);
        System.out.println(c == d);
        System.out.println(d == e);
        System.out.println(e == f);
    }

    public static void main(String[] args) {
        testString();
        testInteger();
    }

}
