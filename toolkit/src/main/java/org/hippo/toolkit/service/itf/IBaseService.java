package org.hippo.toolkit.service.itf;

/**
 * Service接口基类
 * <p>
 * Created by litengfei on 2017/6/20.
 */
public interface IBaseService<T> {

    int insert(T record);
    int insertSelective(T record);
    T selectByPrimaryKey(String id);
    int updateByPrimaryKey(T record);
    int updateByPrimaryKeySelective(T record);
    int deleteByPrimaryKey(String id);

}
