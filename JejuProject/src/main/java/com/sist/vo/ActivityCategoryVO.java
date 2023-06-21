package com.sist.vo;

/*
	accno NUMBER,
   acno NUMBER,
   name  VARCHAR2(60),
   title VARCHAR2(1000),
   poster VARCHAR2 (260),
   link VARCHAR2 (200),
   score NUMBER(2,1),
   review_count NUMBER,
   price NUMBER,
   discount_rate VARCHAR2(20),


*/
public class ActivityCategoryVO {
	private int accno;
	private String name, link;
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public int getAccno() {
		return accno;
	}
	public void setAccno(int accno) {
		this.accno = accno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
