package com.rollingStones.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 申请记录
 * Created by gaoxinzhong on 2017/3/23.
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CourseUserId implements Serializable {

    private static final long serialVersionUID = 997081496001010405L;

    /**id**/
    private Integer id;
    /**课程ID**/
    private Integer courseId;
    /**用户ID**/
    private Integer userId;
    /**用户名**/
    private String userName;
    /**学科名**/
    private String subjectProductName;
    /**手机号**/
    private String telephone;
    /**支付金额**/
    private Double payAmount;

    /**报名时间**/
    private Date createTime;
    /**用户报名状态 0 待审核 1 已报名成功（审核通过） 2 未报名 3 已取消 4 退款待审核 5 已退款 6 已退回（审核未通过）7后台补报 8 报名审核未通过**/
    private Integer status;
    /**1.已点击0.未点击**/
    private Integer onlinePlay;
    /**1.已点击0.未点击**/
    private Integer replay;
    /**最后更新时间**/
    private Date lastUpdateTime;
    /**机构ID**/
    private Integer insId;
    /**报名人**/
    private Integer operatorUser;

    /**课程名称**/
    private String courseName;
    /**参加课程用户的角色**/
    private String userRole;
    /**报名人角色**/
    private String operatorRole;
    /**机构名称**/
    private String insName;

    /**操作人姓名**/
    private String operatorUserName;
    
    /**是否下发评定 0否 1是**/
    private Integer isSend;
    
    /**下发时间**/
    private Date sendTime;

    /** 资源链接 **/
    private String resourceUrl;
    /**  资源文件名 **/
    private String resourceName;

    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 订单状态
     */
    private Integer orderStatus;
    /**
     * 当前订单的实付金额
     */
    private Double orderPrice;
    /** 
     * 优惠了的金额 
     */
    private Double preferencePrice;
    
    /**
     * 状态集合
     */
    private List<Integer> statusList;
    /**
     * 省
     */
    private String provinceName;
    /**
     * 市
     */
    private String cityName;
    /**
     * 区
     */
    private String districtName;

    /**
     * 报名成功时间
     */
    private Date signSuccessTime;

}
