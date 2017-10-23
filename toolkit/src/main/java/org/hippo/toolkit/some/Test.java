package org.hippo.toolkit.some;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by litengfei on 2017/9/6.
 */
public class Test extends Thread {

    private static List<String> resultList = new ArrayList<>();
    private static List<String> resultList1 = new ArrayList<>();
    private static List<String> resultList2 = new ArrayList<>();
    private static List<String> resultList3 = new ArrayList<>();
    private static List<String> resultList4 = new ArrayList<>();
    private static List<String> resultList5 = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println(o(10));
        System.out.println(o("",10));
        System.out.println(o("", 0, 10));

        printAll();
    }

    private static int o(int sum) {
        if (sum <= 0) {
          return 0;
        } if (sum == 1) {
            return 1;
        } else if (sum == 2) {
            return 2;
        } else {
            return o(sum - 1) + o(sum - 2);
        }
    }

    private static int o(String pre, int sum) {
        if (sum <= 0) {
            return 0;
        } if (sum == 1) {
            System.out.println(pre + "1");
            return 1;
        } else if (sum == 2) {
            System.out.println(pre + "11");
            System.out.println(pre + "2");
            return 2;
        } else {
            return o(pre + "1", sum - 1) + o(pre + "2", sum - 2);
        }
    }

    private static int o(String pre, int time, int sum) {
        if (sum <= 0) {
            return 0;
        } if (sum == 1) {
            genResult(pre + "1", time);
            return 1;
        } else if (sum == 2) {
            genResult(pre + "11", time);
            genResult(pre + "2", time + 1);
            return 2;
        } else {
            return o(pre + "1", time, sum - 1) + o(pre + "2", time + 1, sum - 2);
        }
    }

    private static void genResult(String result, int time) {
        switch (time) {
            case 0:
                resultList.add(result);
                break;
            case 1:
                resultList1.add(result);
                break;
            case 2:
                resultList2.add(result);
                break;
            case 3:
                resultList3.add(result);
                break;
            case 4:
                resultList4.add(result);
                break;
            case 5:
                resultList5.add(result);
                break;
        }
    }

    private static void printAll() {
        printResults(resultList);
        printResults(resultList1);
        printResults(resultList2);
        printResults(resultList3);
        printResults(resultList4);
        printResults(resultList5);
    }

    private static void printResults(List<String> resultList) {
        System.out.println("--------");
        for (String result : resultList) {
            System.out.println(result);
        }
        System.out.println("--------");


        Person p1 = new Person();
        p1.setId(1);
        p1.setName("张三");
        p1.setSex(true);
        p1.setFriends(new int[]{2, 3, 4});

        Person p2 = new Person();
        p2.setId(8);
        p2.setName("李四");
        p2.setSex(false);
        p2.setFriends(new int[]{5, 6});

        List<Person> list = new ArrayList();
        list.add(p1);
        list.add(p2);

    }

}
