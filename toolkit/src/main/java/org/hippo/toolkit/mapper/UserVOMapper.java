package org.hippo.toolkit.mapper;

import org.hippo.toolkit.entity.UserVO;

public interface UserVOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user
     *
     * @mbg.generated 2017/06/19
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user
     *
     * @mbg.generated 2017/06/19
     */
    int insert(UserVO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user
     *
     * @mbg.generated 2017/06/19
     */
    int insertSelective(UserVO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user
     *
     * @mbg.generated 2017/06/19
     */
    UserVO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user
     *
     * @mbg.generated 2017/06/19
     */
    int updateByPrimaryKeySelective(UserVO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user
     *
     * @mbg.generated 2017/06/19
     */
    int updateByPrimaryKey(UserVO record);
}