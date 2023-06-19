package com.sist.vo;

/*
	acino           NUMBER,
   accno NUMBER,
   title           VARCHAR2(1000) CONSTRAINT aci_title_nn NOT NULL,
   score           NUMBER(2,1),
   review_count    NUMBER,
   playtime        VARCHAR2(100),
   ticket_option1  VARCHAR2(200),
   ticket_option2  VARCHAR2(200),
   ticket_option3  VARCHAR2(200),
   content_subject VARCHAR2(1000),
   content_cont    CLOB,
   price           NUMBER,
   poster          VARCHAR2(260) CONSTRAINT aci_poster_nn NOT NULL,


*/
public class ActivityInfoVO {
	private int acino, accno, reviewCount, price;
	private String title, playtime, ticketOption1, ticketOption2, ticketOption3,
			contentSubject, contentCont, poster;
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
	public int getReviewCount() {
		return reviewCount;
	}
	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
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
	public String getTicketOption1() {
		return ticketOption1;
	}
	public void setTicketOption1(String ticketOption1) {
		this.ticketOption1 = ticketOption1;
	}
	public String getTicketOption2() {
		return ticketOption2;
	}
	public void setTicketOption2(String ticketOption2) {
		this.ticketOption2 = ticketOption2;
	}
	public String getTicketOption3() {
		return ticketOption3;
	}
	public void setTicketOption3(String ticketOption3) {
		this.ticketOption3 = ticketOption3;
	}
	public String getContentSubject() {
		return contentSubject;
	}
	public void setContentSubject(String contentSubject) {
		this.contentSubject = contentSubject;
	}
	public String getContentCont() {
		return contentCont;
	}
	public void setContentCont(String contentCont) {
		this.contentCont = contentCont;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	
	
}
