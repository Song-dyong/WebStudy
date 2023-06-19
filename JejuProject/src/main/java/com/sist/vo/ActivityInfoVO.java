package com.sist.vo;

/*
	acino           NUMBER,
   accno NUMBER,
   title           VARCHAR2(1000) CONSTRAINT aci_title_nn NOT NULL,		-- 제목
   score           NUMBER(2,1),											-- 평점
   review_count    NUMBER,												-- 리뷰 갯수
   playtime        VARCHAR2(100),										-- 소요시간
   ticket_option1  VARCHAR2(200),										-- 티켓 옵션 
   ticket_option2  VARCHAR2(200),
   ticket_option3  VARCHAR2(200),
   content_subject VARCHAR2(1000),										-- 액티비티 설명 제목
   content_cont    CLOB,												-- 액티비티 설명 내용
   price           NUMBER,												-- 가격
   discount_rate VARCHAR2(20),											-- 할인율
   reviewer VARCHAR2(51),												-- 리뷰한 사람
   review_content VARCHAR2(2000),										-- 리뷰 내용
   hours_use VARCHAR2(1000),											-- 이용시간
   location_name VARCHAR2(1000),										-- 위치 이름
   location_poster VARCHAR2(300),										-- 위치 지도 사진
   how_use VARCHAR2(2000),												-- 이용방법
   poster          VARCHAR2(260) CONSTRAINT aci_poster_nn NOT NULL,		-- 포스터

*/
public class ActivityInfoVO {
	private int acino, accno, review_count, price;
	private String title, playtime, ticket_option1, ticket_option2, ticket_option3, content_subject, content_cont, discount_rate,
					reviewer, review_content, hours_use, location_name, location_poster, how_use, poster;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPlaytime() {
		return playtime;
	}
	public void setPlaytime(String playtime) {
		this.playtime = playtime;
	}
	public String getTicket_option1() {
		return ticket_option1;
	}
	public void setTicket_option1(String ticket_option1) {
		this.ticket_option1 = ticket_option1;
	}
	public String getTicket_option2() {
		return ticket_option2;
	}
	public void setTicket_option2(String ticket_option2) {
		this.ticket_option2 = ticket_option2;
	}
	public String getTicket_option3() {
		return ticket_option3;
	}
	public void setTicket_option3(String ticket_option3) {
		this.ticket_option3 = ticket_option3;
	}
	public String getContent_subject() {
		return content_subject;
	}
	public void setContent_subject(String content_subject) {
		this.content_subject = content_subject;
	}
	public String getContent_cont() {
		return content_cont;
	}
	public void setContent_cont(String content_cont) {
		this.content_cont = content_cont;
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
