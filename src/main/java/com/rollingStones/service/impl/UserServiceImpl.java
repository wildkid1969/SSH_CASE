package com.rollingStones.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.rollingStones.dao.UserDAO;
import com.rollingStones.entity.Page;
import com.rollingStones.entity.User;
import com.rollingStones.service.UserService;

/**
 * 
 * Created by mengya on 2017年6月20日
 *
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	
    @Resource
    private UserDAO userDao;

    public void saveUsers(List<User> us) {
        for (User u : us) {
            userDao.save(u);
        }
    }

    public List<User> getAllUsernames() {
        return userDao.findAll();
    }

	@Override
	public Page<User> getUserByParam(int pageNo,int pageSize, User user) {
		List<Object> list = Lists.newArrayList();
		String hql = "from User u where user_name = ?";
		
		list.add(user.getUserName());
		
		return userDao.pagedByHql(hql, pageNo, pageSize, new Object[]{user.getUserName()});
	}
	
}
