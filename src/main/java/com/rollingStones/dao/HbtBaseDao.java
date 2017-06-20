package com.rollingStones.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.rollingStones.entity.Page;

public interface HbtBaseDao<T> extends BaseDao<T> {

	public List<T> findByHql(String hql, Object... values);

	public List<Map<String, Object>> findBySql(String sql, Object... values);

	public List<T> findByDC(DetachedCriteria dc);

	public T findUniqueByHql(String hql, Object... values);

	public T findUniqueBySql(String sql, Object... values);

	public T findUniqueByDC(DetachedCriteria dc);

	public Page<T> pagedByHql(String hql, int pageNo, int pageSize, Object... values);

	public Page<Map<String, Object>> pagedBySql(boolean total, String sql, int pageNo, int pageSize, Object... values);

	public Page<T> pagedByDC(DetachedCriteria dc, int pageNo, int pageSize);

	public int countByDC(DetachedCriteria dc);

	public int executeUpdateBySql(String sql);

	public int executeUpdateBySql(String sql, Object... values);

	public int executeUpdateByHql(String hql);
}
