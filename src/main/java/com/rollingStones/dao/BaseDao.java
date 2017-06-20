package com.rollingStones.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author shun
 *
 * @param <T>
 */
public interface BaseDao<T> {

	public Object save(Object obj);

	public void deleteById(Serializable id);

	public void update(Object obj);

	public void saveOrUpdate(Object obj);

	public T getById(Serializable id);

	public List<T> getList();
}
