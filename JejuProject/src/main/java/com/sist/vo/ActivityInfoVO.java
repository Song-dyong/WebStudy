package com.sist.vo;

/*
	acino           NUMBER,
   accno NUMBER,
   title           VARCHAR2(1000) CONSTRAINT aci_title_nn NOT NULL,		-- 제목
   score           NUMBER(2,1),											-- 평점
   review_count    VARCHAR2(20),										-- 리뷰 갯수
   price           NUMBER,												-- 가격
   discount_rate VARCHAR2(20),											-- 할인율
   reviewer VARCHAR2(51),												-- 리뷰한 사람
   review_content VARCHAR2(2000),										-- 리뷰 내용
   hours_use VARCHAR2(1000),											-- 이용시간
   location_name VARCHAR2(1000),										-- 위치 안내
   location_poster VARCHAR2(300),										-- 위치 지도 사진
   how_use VARCHAR2(2000),												-- 사용방법
   poster          VARCHAR2(260) CONSTRAINT aci_poster_nn NOT NULL,		-- 포스터

*/
public class ActivityInfoVO {
	private int acino, accno, price;
	private String title, discount_rate, reviewer, review_content, hours_use, location_name, location_poster, how_use, poster, review_count;
	private double score;
	
	
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public int getAcino() {
		return acino;
	}
	public void setAcino(int acino) {
		this.acino = acino;
	}
	public int getAccno() {
		return accno;
	}
	public void setAccno(int accno) {
		this.accno = accno;
	}
	
	public String getReview_count() {
		return review_count;
	}
	public void setReview_count(String review_count) {
		this.review_count = review_count;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDiscount_rate() {
		return discount_rate;
	}
	public void setDiscount_rate(String discount_rate) {
		this.discount_rate = discount_rate;
	}
	public String getReviewer() {
		return reviewer;
	}
	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	public String getReview_content() {
		return review_content;
	}
	public void setReview_content(String review_content) {
		this.review_content = review_content;
	}
	public String getHours_use() {
		return hours_use;
	}
	public void setHours_use(String hours_use) {
		this.hours_use = hours_use;
	}
	public String getLocation_name() {
		return location_name;
	}
	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}
	public String getLocation_poster() {
		return location_poster;
	}
	public void setLocation_poster(String location_poster) {
		this.location_poster = location_poster;
	}
	public String getHow_use() {
		return how_use;
	}
	public void setHow_use(String how_use) {
		this.how_use = how_use;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	
	
}
