package com.rollingStones.entity;

import java.io.Serializable;



public class Lectruer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//columns START
    //讲师ID    
	private java.lang.Integer id;
    //讲师姓名    
	private java.lang.String name;
    //讲师头像    
	private java.lang.String portrait;
    //资历    
	private java.lang.String qualifi;
    //介绍    
	private java.lang.String introduce;
    //头衔    
	private java.lang.String title;
	//columns END

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setPortrait(java.lang.String value) {
		this.portrait = value;
	}
	
	public java.lang.String getPortrait() {
		return this.portrait;
	}
	public void setQualifi(java.lang.String value) {
		this.qualifi = value;
	}
	
	public java.lang.String getQualifi() {
		return this.qualifi;
	}
	public void setIntroduce(java.lang.String value) {
		this.introduce = value;
	}
	
	public java.lang.String getIntroduce() {
		return this.introduce;
	}
	public void setTitle(java.lang.String value) {
		this.title = value;
	}
	
	public java.lang.String getTitle() {
		return this.title;
	}

}

