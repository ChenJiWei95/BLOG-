package com.blog.dao;

import java.util.List;
import com.blog.entity.User;

public interface UserDao {
//	User queryUser(User user) throws Exception;
//	List<User> queryUsers(Integer id) throws Exception;
	User queryUserByUsername(String username) throws Exception;
}
