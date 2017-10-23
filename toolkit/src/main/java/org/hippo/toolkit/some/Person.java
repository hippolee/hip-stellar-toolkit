package org.hippo.toolkit.some;

/**
 * Created by litengfei on 2017/9/29.
 */
public class Person {

    /** 编码 */
    private int id;
    /** 姓名 */
    private String name;
    /** 性别 */
    private boolean sex;
    /** 好友 */
    private int[] friends;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public int[] getFriends() {
        return friends;
    }

    public void setFriends(int[] friends) {
        this.friends = friends;
    }
}
