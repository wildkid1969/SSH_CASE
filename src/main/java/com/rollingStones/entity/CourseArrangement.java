package com.rollingStones.entity;

import java.io.Serializable;
import java.util.Date;


public class CourseArrangement implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//columns START
    //id    
	private java.lang.Integer id;
    //培训课程ID    
	private java.lang.Integer courseId;
    //课程排序    
	private java.lang.String courseSort;
    //课程名称    
	private java.lang.String arrangementName;
	//课表展示时间
	private String casTime;
    //开始时间
	private java.util.Date startTime;
    //结束时间    
	private java.util.Date endTime;
    //课程描述    
	private java.lang.String descripe;
    //0.可用1.不可用    
	private java.lang.Integer status;
    //创建时间    
	private java.util.Date createTime;
    //更新时间
	private java.util.Date updateTime;
	
    //是否需要签到 0否 1是    
	private Integer isSign;
	//扣除的分数
	private Double reduceScore;
    //签到开始时间    
	private java.util.Date signStartTime;
    //签到结束时间    
	private java.util.Date signEndTime;

	//二维码地址
    private String imageUrl;
	
	private Integer userSignStatus=0;//用户签到状态  0未到 1已签 2迟到

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

    public String getCourseSort() {
        return courseSort;
    }

    public void setCourseSort(String courseSort) {
        this.courseSort = courseSort;
    }

    public String getArrangementName() {
        return arrangementName;
    }

    public void setArrangementName(String arrangementName) {
        this.arrangementName = arrangementName;
    }

    public String getCasTime() {
        return casTime;
    }

    public void setCasTime(String casTime) {
        this.casTime = casTime;
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

    public String getDescripe() {
        return descripe;
    }

    public void setDescripe(String descripe) {
        this.descripe = descripe;
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

	public Integer getIsSign() {
		return isSign;
	}

	public void setIsSign(Integer isSign) {
		this.isSign = isSign;
	}

	public java.util.Date getSignStartTime() {
		return signStartTime;
	}

	public void setSignStartTime(java.util.Date signStartTime) {
		this.signStartTime = signStartTime;
	}

	public java.util.Date getSignEndTime() {
		return signEndTime;
	}

	public void setSignEndTime(java.util.Date signEndTime) {
		this.signEndTime = signEndTime;
	}

	public Integer getUserSignStatus() {
		return userSignStatus;
	}

	public void setUserSignStatus(Integer userSignStatus) {
		this.userSignStatus = userSignStatus;
	}

	public Double getReduceScore() {
		return reduceScore;
	}

	public void setReduceScore(Double reduceScore) {
		this.reduceScore = reduceScore;
	}

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

