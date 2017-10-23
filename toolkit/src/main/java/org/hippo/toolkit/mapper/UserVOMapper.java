package org.hippo.toolkit.mapper;

import org.hippo.toolkit.entity.UserVO;

public interface UserVOMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserVO record);

    int insertSelective(UserVO record);

    UserVO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserVO record);

    int updateByPrimaryKey(UserVO record);

    UserVO selectByMobile(String mobile);
}