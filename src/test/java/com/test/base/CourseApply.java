package com.test.base;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户申请课程表
 * @version 1.0
 * @author
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CourseApply implements Serializable {

	// 主键id
	private Integer id;
	// 课程id
	private Integer courseId;
	// 机构id
	private Integer insId;
	// 申请用户id
	private Integer applyUserId;
	// 申请时间
	private Date applyTime;
	// 申请备注
	private String applyRemark;
	// 申请状态
	private Integer applyStatus;
	// 处理结果
	private Integer dealStatus;
	// 报名人用户id
	private Integer signUserId;
	// 更新时间
	private Date updateTime;

}
