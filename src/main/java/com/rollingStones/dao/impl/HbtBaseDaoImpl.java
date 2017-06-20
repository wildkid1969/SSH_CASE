package com.rollingStones.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.rollingStones.dao.HbtBaseDao;
import com.rollingStones.entity.Page;
import com.rollingStones.utils.GenericsUtils;

@Repository
public class HbtBaseDaoImpl<T> implements HbtBaseDao<T> {

	/**
	 * 定义SessionFactory，用于获取session； 通过session进行实际的数据库操作
	 * 注解式的依赖注入,默认是按名称进行查找(在applicationContext.xml中查找)
	 */
	@Resource(name = "sessionFactory")
	protected SessionFactory sessionFactory;

	/**
	 * 泛型对应的具体类型，在构造方法中通过反射获得
	 */
	private Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public HbtBaseDaoImpl() {
		super();
		entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	}

	/**
	 * 保存对象的方法
	 * 
	 * @param obj
	 *            需要保存的对象实例
	 */
	@Override
	public Object save(Object obj) {
		// 得到Spring容器里正在管理的session对象
		// 使用persist()方法进行持久化(也就是保存),相当于save()方法,但persist()方法更符合J2EE规范
		Object result = sessionFactory.getCurrentSession().save(obj);
		return result;
	}

	/**
	 * 根据对象编号删除对象的方法
	 * 
	 * @param id
	 *            对象编号
	 */
	@Override
	public void deleteById(Serializable id) {
		// 此处不建议使用getXXX()方法来获取实体对象,因为getXXX()有一个装配的过程，效率不高
		// 使用load()方法来获取实体对象，效率比使用getXXX()方法高
		sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().get(entityClass, id));
	}

	/**
	 * 更新对象的方法
	 * 
	 * @param obj
	 *            需要更新的对象实例
	 */
	@Override
	public void update(Object obj) {
		// 使用merge()方法更新,相当于update()方法,但merge() 方法更符合J2EE规范
		sessionFactory.getCurrentSession().merge(obj);
	}

	/**
	 * 保存或更新对象的方法，具体是保存还是更新由Hibernate判断
	 * 
	 * @param obj
	 *            需要保存或更新的对象实例
	 */
	@Override
	public void saveOrUpdate(Object obj) {
		sessionFactory.getCurrentSession().saveOrUpdate(obj);
	}

	/**
	 * 根据对象编号获得对象的方法
	 * 
	 * @param id
	 *            对象编号
	 * @return 对象实例
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T getById(Serializable id) {
		return (T) sessionFactory.getCurrentSession().get(entityClass, id);
	}

	/**
	 * 获取对象实例的集合的方法
	 * 
	 * @return 对象集合
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getList() {
		String hql = "from " + entityClass.getName();
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

	/**
	 * 根据hql获取对象列表的方法
	 * 
	 * @param hql
	 *            HQL语句
	 * @param values
	 *            参数对象
	 * @return 对象集合
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByHql(String hql, Object... values) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		int i = 0;
		for (Object obj : values) {
			query.setParameter(i, obj);
			i++;
		}
		return query.list();
	}

	/**
	 * 根据SQL获取对象列表的方法
	 * 
	 * @param sql
	 *            SQL语句
	 * @param values
	 *            参数对象
	 * @return 对象集合
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findBySql(String sql, Object... values) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		int i = 0;
		for (Object obj : values) {
			query.setParameter(i, obj);
			i++;
		}
		return query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	}

	/**
	 * 使用离线查询，根据查询条件获取对象集合的方法
	 * 
	 * @param dc
	 *            离线查询的条件
	 * @return 对象集合
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByDC(DetachedCriteria dc) {
		return dc.getExecutableCriteria(sessionFactory.getCurrentSession()).list();
	}

	/**
	 * 根据hql获取唯一对象的方法
	 * 
	 * @param hql
	 *            HQL语句
	 * @param values
	 *            参数对象
	 * @return 对象实例
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T findUniqueByHql(String hql, Object... values) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		int i = 0;
		for (Object obj : values) {
			query.setParameter(i, obj);
			i++;
		}
		return (T) query.uniqueResult();
	}

	/**
	 * 根据Sql获取单一对象的方法
	 * 
	 * @param sql
	 *            SQL语句
	 * @param 参数对象
	 * @return 对象实例
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T findUniqueBySql(String sql, Object... values) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(entityClass);
		int i = 0;
		for (Object obj : values) {
			query.setParameter(i, obj);
			i++;
		}
		return (T) query.uniqueResult();
	}

	/**
	 * 使用离线查询，根据查询条件获取单一对象的方法
	 * 
	 * @param dc
	 *            离线查询的条件
	 * @return 对象实例
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T findUniqueByDC(DetachedCriteria dc) {
		return (T) dc.getExecutableCriteria(sessionFactory.getCurrentSession()).uniqueResult();
	}

	/**
	 * 通过hql分页获取对象集合的方法
	 * 
	 * @param hql
	 *            HQL语句
	 * @param pageNo
	 *            页数
	 * @param pageSize
	 *            每页记录数
	 * @param values
	 *            参数列表
	 * @return 持久层分页对象
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Page<T> pagedByHql(String hql, int pageNo, int pageSize, Object... values) {
		String countQueryString = "select count(*) " + removeSelect(removeOrders(hql));

		List<T> countList = findByHql(countQueryString, values);
		long totalSize = (Long) countList.get(0);
		if (totalSize < 1) {
			return new Page<T>();
		}
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		Query query = createQuery(hql, values);
		List<T> list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
		return new Page<T>(startIndex, totalSize, pageSize, list);
	}

	/**
	 * 通过sql分页获取对象集合的方法
	 * 
	 * @param sql
	 *            SQL语句
	 * @param pageNo
	 *            页数
	 * @param pageSize
	 *            每页记录数
	 * @param values
	 *            参数列表
	 * @return 持久层分页对象
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Page<Map<String, Object>> pagedBySql(boolean total, String sql, int pageNo, int pageSize, Object... values) {
		long totalCount = 0;
		if (total) {
			String countQueryString = "select count(1) " + removeSelect(removeOrders(sql));
			List<Map<String, Object>> countList = findBySql(countQueryString, values);
			Map<String, Object> map = (Map<String, Object>) countList.get(0);
			Object obj = map.get("count(1)");
			if (obj instanceof Integer) {
				totalCount = ((Integer) obj).longValue();
			} else if (obj instanceof Long) {
				totalCount = (Long) obj;
			} else if (obj instanceof BigDecimal) {
				totalCount = ((BigDecimal) obj).longValue();
			} else if (obj instanceof BigInteger) {
				totalCount = ((BigInteger) obj).longValue();
			}
			if (totalCount < 1) {
				return new Page<Map<String, Object>>();
			}

		}
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		Query query = createSqlQuery(sql, values);
		List<Map<String, Object>> list = query.setFirstResult(startIndex).setMaxResults(pageSize)
				.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		return new Page<Map<String, Object>>(startIndex, totalCount, pageSize, list);
	}

	/**
	 * 使用离线查询，根据查询条件分页获取对象集合的方法
	 * 
	 * @param dc
	 *            离线查询条件
	 * @param pageNo
	 *            页数
	 * @param pageSize
	 *            每页记录数
	 * @return 持久层分页对象
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Page<T> pagedByDC(DetachedCriteria dc, int pageNo, int pageSize) {
		// 获取总记录数
		long totalCount = (long) dc.getExecutableCriteria(sessionFactory.getCurrentSession()).list().size();
		if (totalCount < 1) {
			return new Page<T>();
		}
		// 获取起始位置
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<T> list = dc.getExecutableCriteria(sessionFactory.getCurrentSession()).setFirstResult(startIndex)
				.setMaxResults(pageSize).list();
		return new Page<T>(startIndex, totalCount, pageSize, list);
	}

	/**
	 * 使用离线查询，根据查询条件获取对象数量的方法
	 * 
	 * @param dc
	 *            离线查询条件
	 * @return 对象数量
	 */
	public int countByDC(DetachedCriteria dc) {
		return dc.getExecutableCriteria(sessionFactory.getCurrentSession()).list().size();
	}

	/**
	 * 私有方法，创建一个Query对象。
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	private Query createQuery(String hql, Object... values) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query;
	}

	/**
	 * 私有方法，创建一个Query对象。
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	private Query createSqlQuery(String sql, Object... values) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query;
	}

	/**
	 * 私有方法，去除hql的select子句。
	 * 
	 * @param hql
	 * @return
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeSelect(String hql) {
		int beginPos = hql.toLowerCase().indexOf("from");
		return hql.substring(beginPos);
	}

	/**
	 * 私有方法，去除hql的orderBy子句。
	 * 
	 * @param hql
	 * @return
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeOrders(String hql) {
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 执行sql语句进行更新
	 * 
	 * @param sql
	 * @return
	 * @see tt.app.center.common.dao.impl.IHbtBaseDao#executeUpdateBySql(java.lang.String)
	 */
	@Override
	public int executeUpdateBySql(String sql) {
		if (StringUtils.isEmpty(sql)) {
			return 0;
		}
		return sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

	@Override
	public int executeUpdateBySql(String sql, Object... values) {
		if (StringUtils.isEmpty(sql)) {
			return 0;
		}
		return createSqlQuery(sql, values).executeUpdate();
	}

	@Override
	public int executeUpdateByHql(String hql) {
		if (StringUtils.isEmpty(hql)) {
			return 0;
		}
		return sessionFactory.getCurrentSession().createQuery(hql).executeUpdate();
	}

}
