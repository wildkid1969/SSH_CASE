package com.rollingStones.service;

import java.util.List;

import com.rollingStones.entity.Page;
import com.rollingStones.entity.User;

/**
 * Created by mark on 4/24/15.
 */
public interface UserService {
    public void saveUsers(List<User> us);
    public List<User> getAllUsernames();
    public Page<User> getUserByParam(int pageNo,int pageSize, User user);
}
