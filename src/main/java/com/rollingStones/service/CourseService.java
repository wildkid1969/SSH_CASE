package com.rollingStones.service;

import com.rollingStones.entity.Course;
import com.rollingStones.entity.Page;

/**
 * 
 * @author mengya
 *
 */
public interface CourseService {

	/**
	 *获取课程列表分页
	 * @param scanType 浏览类型 1近期 2往期 默认1
	 * @param insId 机构ID
	 * @param userId 用户i
	 * @param pageNum 当前页码 默认1
	 * @param pageSize 页面大小
	 * @return
	 */
	public Page<Course> getCourseListByParam(int scanType,Long userId,Integer insId,int pageNum,int pageSize);

	/**
	 *课程详情
	 * @param courseId 课程ID
	 * @param insId 机构ID
	 * @param userId 用户id
	 * @param detailLevel 详细类型 0简单 1详细  默认1
	 * @return
	 */
	public Course getCourseDetailsByParam(Long courseId,Long insId,Long userId,Integer detailLevel);
}
