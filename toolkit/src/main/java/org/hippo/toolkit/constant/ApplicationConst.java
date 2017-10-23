package org.hippo.toolkit.constant;

/**
 * 应用常量
 * <p>
 * Created by litengfei on 2017/5/26.
 */
public class ApplicationConst {

    public enum ClientType {
        WEB(0, "WEB"),
        PC(1, "PC"),
        ANDROID(2, "ANDROID"),
        IOS(4, "IOS");

        private Integer id;
        private String name;

        ClientType(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public static ClientType get(Integer id) {
            for (ClientType ct : ClientType.values()) {
                if (ct.getId() == id) {
                    return ct;
                }
            }
            return null;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
