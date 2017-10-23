package org.hippo.toolkit.some;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by litengfei on 2017/10/13.
 */
public class Test1 {

    public static void main(String[] args) {

    }

    private static void print() {
        StringBuffer sb = new StringBuffer();

        Character[] sources = new Character[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
                'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        ArrayDeque<List<Character>> q = new ArrayDeque<>();
        for (Character c : sources) {
            List<Character> r = new ArrayList<>();
            r.add(c);
            append(sb, r);
            q.add(r);
        }
        while (q.size() > 0) {

        }


        System.out.println(sb.toString());
    }

    private static void append(StringBuffer sb, List<Character> chars) {
        sb.append("(");
        for (Character c : chars) {
            sb.append(c);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
    }

}
