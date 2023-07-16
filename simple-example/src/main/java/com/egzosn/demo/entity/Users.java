package com.egzosn.demo.entity;


/**
* ta 实体类
* 
* @author egan
* email egzosn@gmail.com
* date 2018-5-10 22:00:30
*/ 
public class Users {

	 /**
	 * id
	 */
	private Integer id;
	 /**
	 * 名称
	 */
	private String cname;

	private String mea;

	private Integer count;

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
	}

	public void setCname(String cname){
		this.cname = cname;
	}

	public String getCname(){
		return cname;
	}

	public String getMea() {
		return mea;
	}

	public void setMea(String mea) {
		this.mea = mea;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}

