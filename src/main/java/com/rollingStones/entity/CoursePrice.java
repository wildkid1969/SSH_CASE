package com.rollingStones.entity;

import java.io.Serializable;
import java.util.Date;

public class CoursePrice implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//columns START
    //id    
	private java.lang.Integer id;
    //培训课程ID    
	private java.lang.Integer courseId;
    //折扣价格    
	private java.lang.Double discountPrice;
    //开始时间    
	private java.util.Date startTime;
    //结束时间    
	private java.util.Date endTime;
    //0.可用1.不可用    
	private java.lang.Integer status;
    //创建时间    
	private java.util.Date createTime;
    //更新时间
	private java.util.Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}

