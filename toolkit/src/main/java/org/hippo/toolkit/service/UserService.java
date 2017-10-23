package org.hippo.toolkit.service;

import org.hippo.toolkit.entity.UserVO;
import org.hippo.toolkit.mapper.UserVOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by litengfei on 2017/6/19.
 */
@Service("userService")
@Transactional
public class UserService {

    @Autowired
    private UserVOMapper userVOMapper;

    public UserVO getUserById(Long id) {
        return userVOMapper.selectByPrimaryKey(id);
    }

    public UserVO getUserByMobile(String mobile) {
        return userVOMapper.selectByMobile(mobile);
    }

}
