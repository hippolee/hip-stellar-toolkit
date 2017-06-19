package org.hippo.toolkit.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Mysql数据对象基类
 * <p>
 * Created by litengfei on 2017/6/16.
 */
public abstract class BaseVO implements Serializable {

    /** 对象ID */
    private Long id;
    /** 是否删除 */
    private Boolean dr;
    /** 创建时间 */
    private Date createTime;
    /** 修改时间 */
    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDr() {
        return dr;
    }

    public void setDr(Boolean dr) {
        this.dr = dr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
