package com.rollingStones.service.impl;

import org.springframework.stereotype.Service;

import com.rollingStones.entity.Course;
import com.rollingStones.entity.Page;
import com.rollingStones.service.CourseService;

@Service("courseService")
public class CourseServiceImpl implements CourseService {
	

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
