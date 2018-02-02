package com.rollingStones.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 
 * Created by mengya on 2017年6月20日
 *
 */
@Entity
@Table
public class Course implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//columns START
    //课程ID    
	@Id
    @GeneratedValue
	private java.lang.Long id;
    //课程名称    
	@Column(name="name")
	private java.lang.String name;
    //课程状态    
	@Column(name="status")
	private java.lang.String status;
    //课程类型：直播，现场    
	@Column(name="course_type")
	private java.lang.String courseType;
    //课程图片    
	@Column(name="img_url")
	private java.lang.String imgUrl;
    //开始时间    
	@Column(name="start_time")
	private java.util.Date startTime;
    //结束时间    
	@Column(name="end_time")
	private java.util.Date endTime;
    //报名条件    
	@Column(name="conditions")
	private java.lang.String conditions;
    //课程介绍    
	@Column(name="introduce")
	private java.lang.String introduce;
    //适合对象    
	@Column(name="suit")
	private java.lang.String suit;
    //创建时间    
	@Column(name="create_time")
	private java.util.Date createTime;
    //直播地址(学生) 
	@Column(name="online_play")
	private java.lang.String onlinePlay;
    //回放地址    
	@Column(name="replay")
	private java.lang.String replay;
	
    //收费类型 0:为不收费 1:为收费    
	@Column(name="price_type")
	private java.lang.Integer priceType;
    //现场上课地点    
	@Column(name="real_address")
	private java.lang.String realAddress;
	//课程原价
	@Column(name="original_price")
	private Double originalPrice;
	//课程人数限制
	@Column(name="applicationNum")
	private Integer applicationNum;
	//是否需要审核 0否 1是
	@Column(name="is_check")
	private Integer isCheck;
	
	//报名人数类型 0:为不限制 1:为限制    
	private java.lang.Integer applicationType;
    //报名时间类型 0:为不限制 1:为限制    
	private java.lang.Integer courseTimelimit;
    //报名开始时间    
	private java.util.Date registrationStarttime;
    //报名截止时间    
	private java.util.Date registrationEndtime;
	
	
	private Long userId;//用户id
	private Integer detailLevel=1;//课程详情级别 0只查普通详情 1默认额外去查价格、安排、讲师等

	private Integer userCnt;//用户人数
	private Integer insUserCnt;//机构下某一课程的用户的报名人数
	private Integer insId;//机构id
	private Double discount;//当前折扣价
	private Integer isDiscount = 0;//是否有折扣 0无 1有
	private Integer myEnrollState;//当前用户的报名状态: 0 待审核 1 已报名成功（审核通过） 2 未报名 3 已取消 4 退款待审核 5 已退款 6 已退回（审核未通过）7后台补报
	private List<CoursePrice> coursePriceList;//课程价格集合
	private List<CourseArrangement> arrangementList;//课程安排集合
	private List<Lectruer> lectruerList;//讲师集合
	
	
	private Integer scanType=1;//浏览类型 1近期课程 2往期课程
	private Integer startNum;//limit分页开始
	private Integer pageSize;//limit分页大小
	private Integer issuedEvaluate; // 下发评定报告 1 是 0 否
	//columns END

	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
	
	public java.lang.String getStatus() {
		return this.status;
	}
	public void setCourseType(java.lang.String value) {
		this.courseType = value;
	}
	
	public java.lang.String getCourseType() {
		return this.courseType;
	}
	public void setImgUrl(java.lang.String value) {
		this.imgUrl = value;
	}
	
	public java.lang.String getImgUrl() {
		return this.imgUrl;
	}
	public void setStartTime(java.util.Date value) {
		this.startTime = value;
	}
	
	public java.util.Date getStartTime() {
		return this.startTime;
	}
	public void setEndTime(java.util.Date value) {
		this.endTime = value;
	}
	
	public java.util.Date getEndTime() {
		return this.endTime;
	}
	public void setConditions(java.lang.String value) {
		this.conditions = value;
	}
	
	public Integer getMyEnrollState() {
		return myEnrollState;
	}

	public void setMyEnrollState(Integer myEnrollState) {
		this.myEnrollState = myEnrollState;
	}

	public java.lang.String getConditions() {
		return this.conditions;
	}
	public void setIntroduce(java.lang.String value) {
		this.introduce = value;
	}
	
	public java.lang.String getIntroduce() {
		return this.introduce;
	}
	public void setSuit(java.lang.String value) {
		this.suit = value;
	}
	
	public java.lang.String getSuit() {
		return this.suit;
	}
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public void setOnlinePlay(java.lang.String value) {
		this.onlinePlay = value;
	}
	
	public Integer getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
	}

	public java.lang.String getOnlinePlay() {
		return this.onlinePlay;
	}
	public void setReplay(java.lang.String value) {
		this.replay = value;
	}
	
	public java.lang.String getReplay() {
		return this.replay;
	}
	
	public Integer getApplicationNum() {
		return applicationNum;
	}

	public void setApplicationNum(Integer applicationNum) {
		this.applicationNum = applicationNum;
	}
	public List<CourseArrangement> getArrangementList() {
		return arrangementList;
	}

	public void setArrangementList(List<CourseArrangement> arrangementList) {
		this.arrangementList = arrangementList;
	}

	public List<Lectruer> getLectruerList() {
		return lectruerList;
	}

	public void setLectruerList(List<Lectruer> lectruerList) {
		this.lectruerList = lectruerList;
	}

	public java.lang.Integer getPriceType() {
		return priceType;
	}

	public void setPriceType(java.lang.Integer priceType) {
		this.priceType = priceType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getInsId() {
		return insId;
	}

	public void setInsId(Integer insId) {
		this.insId = insId;
	}

	public java.lang.String getRealAddress() {
		return realAddress;
	}

	public void setRealAddress(java.lang.String realAddress) {
		this.realAddress = realAddress;
	}

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public List<CoursePrice> getCoursePriceList() {
		return coursePriceList;
	}

	public void setCoursePriceList(List<CoursePrice> coursePriceList) {
		this.coursePriceList = coursePriceList;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Integer getUserCnt() {
		return userCnt;
	}

	public void setUserCnt(Integer userCnt) {
		this.userCnt = userCnt;
	}

	public Integer getDetailLevel() {
		return detailLevel;
	}

	public void setDetailLevel(Integer detailLevel) {
		this.detailLevel = detailLevel;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Integer getIsDiscount() {
		return isDiscount;
	}

	public void setIsDiscount(Integer isDiscount) {
		this.isDiscount = isDiscount;
	}

	public Integer getScanType() {
		return scanType;
	}

	public void setScanType(Integer scanType) {
		this.scanType = scanType;
	}

	public Integer getStartNum() {
		return startNum;
	}

	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public java.lang.Integer getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(java.lang.Integer applicationType) {
		this.applicationType = applicationType;
	}

	public java.lang.Integer getCourseTimelimit() {
		return courseTimelimit;
	}

	public void setCourseTimelimit(java.lang.Integer courseTimelimit) {
		this.courseTimelimit = courseTimelimit;
	}

	public java.util.Date getRegistrationStarttime() {
		return registrationStarttime;
	}

	public void setRegistrationStarttime(java.util.Date registrationStarttime) {
		this.registrationStarttime = registrationStarttime;
	}

	public java.util.Date getRegistrationEndtime() {
		return registrationEndtime;
	}

	public void setRegistrationEndtime(java.util.Date registrationEndtime) {
		this.registrationEndtime = registrationEndtime;
	}

	public Integer getIssuedEvaluate() {
		return issuedEvaluate;
	}

	public void setIssuedEvaluate(Integer issuedEvaluate) {
		this.issuedEvaluate = issuedEvaluate;
	}

	public Integer getInsUserCnt() {
		return insUserCnt;
	}

	public void setInsUserCnt(Integer insUserCnt) {
		this.insUserCnt = insUserCnt;
	}

}

