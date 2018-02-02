package com.rollingStones.dao.impl;

import com.rollingStones.dao.CourseDao;
import com.rollingStones.entity.Course;
import com.rollingStones.entity.Page;

public class CourseDaoImpl extends HbtBaseDaoImpl<Course> implements CourseDao{

	@Override
	public Page<Course> getCourseListByParam(int scanType, Long userId,
			Integer insId, int pageNum, int pageSize) {
		return null;
	}

	@Override
	public Course getCourseDetailsByParam(Long courseId, Long insId,
			Long userId, Integer detailLevel) {
		return null;
	}

}
