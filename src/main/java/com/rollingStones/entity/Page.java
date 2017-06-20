package com.rollingStones.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 持久层(PO)的分页对象
 * 
 */
public class Page<T> implements Serializable {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = -6744882162751796931L;
	
	/**
	 * 默认的每页记录数，20
	 */
	private static int DEFAULT_PAGE_SIZE = 20;

	/**
	 * 每页的记录数
	 */
	private int pageSize = DEFAULT_PAGE_SIZE;

	/**
	 * 起始位置，当前页第一条数据在List中的位置，从0开始
	 */
	private long start;

	/**
	 * 结果列表，当前页中存放的记录，类型一般为List
	 */
	private List<T> list;

	/**
	 * 总记录数
	 */
	private long totalSize;

	/**
	 * 默认的构造方法
	 */
	public Page() {
		this(0, 0, 20, new ArrayList<T>());
	}

	/**
	 * 构造方法
	 * @param start 起始位置
	 * @param totalSize 总记录数
	 * @param pageSize 每页的记录数
	 * @param list 结果列表
	 */
	public Page(long start, long totalSize, int pageSize, List<T> list) {
		this.start = start;
		this.totalSize = totalSize;
		this.pageSize = pageSize;
		this.list = list;
	}	

	/**
	 * 获取起始位置
	 * @return 起始位置
	 */
	public long getStart() {
		return start;
	}
	
	/**
	 * 设置起始位置
	 * @param start 起始位置
	 */
	public void setStart(long start) {
		this.start = start;
	}
	
	/**
	 * 获取结果列表
	 * @return 结果列表
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * 设置结果列表
	 * @param list 结果列表
	 */
	public void setList(List<T> list) {
		this.list = list;
	}
	
	/**
	 * 获取总记录数
	 * @return 总记录数
	 */
	public long getTotalSize() {
		return this.totalSize;
	}
	
	/**
	 * 设置总记录数
	 * @param totalSize 总记录数
	 */
	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}
	
	/**
	 * 获取每页的记录数
	 * @return 每页的记录数
	 */
	public int getPageSize() {
		return this.pageSize;
	}
	
	/**
	 * 设置每页的记录数
	 * @param pageSize 每页的记录数
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * 取总页数
	 * @return 总页数
	 */
	public long getTotalPageCount() {
		return totalSize % pageSize == 0 ? totalSize / pageSize : totalSize	/ pageSize + 1;
	}
	
	/**
	 * 取当前的页号
	 * @return 当前的页号
	 */
	public long getCurrentPageNo(){
		return start/pageSize+1;
	}
	
	/**
	 * 该页是否有下一页.
	 */
	public boolean hasNextPage() {
		return this.getCurrentPageNo() < this.getTotalPageCount() - 1;
	}

	/**
	 * 该页是否有上一页.
	 */
	public boolean hasPreviousPage() {
		return this.getCurrentPageNo() > 1;
	}

	/**
	 * 获取任一页第一条数据在数据集的位置，每页条数使用默认值.
	 *
	 * @see #getStartOfPage(int,int)
	 */
	protected static int getStartOfPage(int pageNo) {
		if(pageNo<1){
			pageNo = 1;
		}
		return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
	}

	/**
	 * 获取任一页第一条数据在数据集的位置.
	 *
	 * @param pageNo   从1开始的页号
	 * @param pageSize 每页记录条数
	 * @return 该页第一条数据
	 */
	public static int getStartOfPage(int pageNo, int pageSize) {
		if(pageNo<1){
			pageNo = 1;
		}
		if(pageSize<1){
			pageSize = DEFAULT_PAGE_SIZE;
		}
		return (pageNo - 1) * pageSize;
	}

	@Override
	public String toString() {
		return "Page [pageSize=" + pageSize + ", start=" + start
				+ ", totalSize=" + totalSize + "]";
	}

}
