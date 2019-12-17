package com.hrm.dao;

import com.hrm.bean.User;
import com.hrm.bean.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    //通过用户名得到用户
    User getUserByName(String username);

    User getUserByPwd(String password);

    //通过昵称得到用户
    User getUserByNickName(String nickname);

    //通过邮箱得到用户
    User getUserByEmail(String email);

    //通过电话号码得到用户
    User getUserByPhone(String phone);

    //通过Id得到用户
    User getUserById(String id);

    //通过邮箱重置用户密码
    int resetPwdByEmail(User user);

    //新添用户
    int insertUser(User user);

    //更新用户信息
    int updateUser(User user);

    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}