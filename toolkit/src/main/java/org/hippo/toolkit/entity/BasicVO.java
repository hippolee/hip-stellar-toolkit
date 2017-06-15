package org.hippo.toolkit.entity;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * MongoDB数据对象实体
 * <p>
 * Created by litengfei on 2017/6/15.
 */
public abstract class BasicVO implements Serializable {

    public static final String FIELD_ID = "_id";
    public static final String FIELD_CREATIONDATE = "creationDate";
    public static final String FIELD_MODIFICATIONDATE = "modificationDate";

    @Id
    private String _id;

    /** 创建时间 **/
    private Date creationDate;

    /** 修改时间 **/
    private Date modificationDate;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BasicVO basicVO = (BasicVO) o;

        if (_id != null ? !_id.equals(basicVO._id) : basicVO._id != null) {
            return false;
        }
        if (creationDate != null ? !creationDate.equals(basicVO.creationDate) : basicVO.creationDate != null) {
            return false;
        }
        return modificationDate != null ? modificationDate.equals(basicVO.modificationDate) : basicVO.modificationDate == null;
    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (modificationDate != null ? modificationDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BasicVo [creationDate=" + creationDate + ", modificationDate=" + modificationDate + "]";
    }
}
