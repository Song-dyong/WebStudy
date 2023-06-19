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
	private int accno, review_count, price;
	private double score;
	private String name, title, poster, link, discount_rate;
	
	public int getAccno() {
		return accno;
	}
	public void setAccno(int accno) {
		this.accno = accno;
	}
	public int getReview_count() {
		return review_count;
	}
	public void setReview_count(int review_count) {
		this.review_count = review_count;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDiscount_rate() {
		return discount_rate;
	}
	public void setDiscount_rate(String discount_rate) {
		this.discount_rate = discount_rate;
	}
	
	
}
