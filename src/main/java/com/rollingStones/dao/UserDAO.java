package com.rollingStones.dao;

import com.rollingStones.entity.Page;
import com.rollingStones.entity.User;

import java.util.List;

/**
 * Created by mark on 4/24/15.
 */
public interface UserDAO {
    public int save(User u);
    public List<User> findAll();
    public Page<User> pagedByHql(String hql, int pageNo, int pageSize, Object... values);
}
