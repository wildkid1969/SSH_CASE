package com.rollingStones.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rollingStones.dao.UserDAO;
import com.rollingStones.entity.User;

/**
 * Created by mark on 4/24/15.
 */

@Repository
public class UserDAOImpl extends HbtBaseDaoImpl<User> implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public int save(User u) {
        return (Integer) sessionFactory.getCurrentSession().save(u);
    }

    @SuppressWarnings("unchecked")
	public List<User> findAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        return criteria.list();
    }
}
