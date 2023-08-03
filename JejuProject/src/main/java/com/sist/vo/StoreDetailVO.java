package com.sist.vo;

/*
	wsno NUMBER,
	main_poster varchar2(3000),
	detail_poster varchar2(3000),
	parti_count NUMBER,
	tag varchar2(100),
	detail_intro CLOB,
	goods_title varchar2(2000),
	goods_subtitle varchar2(2000),
	jjim_count NUMBER,
	scno NUMBER,
	acno NUMBER DEFAULT 2, 		
*/
public class StoreDetailVO {
	private int wsno, jjim_count,parti_count,scno,acno;
	private String main_poster, detail_poster,tag,detail_intro,goods_title,goods_subtitle;
	private double score;
	
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public int getWsno() {
		return wsno;
	}
	public void setWsno(int wsno) {
		this.wsno = wsno;
	}
	public int getJjim_count() {
		return jjim_count;
	}
	public void setJjim_count(int jjim_count) {
		this.jjim_count = jjim_count;
	}
	public int getParti_count() {
		return parti_count;
	}
	public void setParti_count(int parti_count) {
		this.parti_count = parti_count;
	}
	public int getScno() {
		return scno;
	}
	public void setScno(int scno) {
		this.scno = scno;
	}
	public int getAcno() {
		return acno;
	}
	public void setAcno(int acno) {
		this.acno = acno;
	}
	public String getMain_poster() {
		return main_poster;
	}
	public void setMain_poster(String main_poster) {
		this.main_poster = main_poster;
	}
	public String getDetail_poster() {
		return detail_poster;
	}
	public void setDetail_poster(String detail_poster) {
		this.detail_poster = detail_poster;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getDetail_intro() {
		return detail_intro;
	}
	public void setDetail_intro(String detail_intro) {
		this.detail_intro = detail_intro;
	}
	public String getGoods_title() {
		return goods_title;
	}
	public void setGoods_title(String goods_title) {
		this.goods_title = goods_title;
	}
	public String getGoods_subtitle() {
		return goods_subtitle;
	}
	public void setGoods_subtitle(String goods_subtitle) {
		this.goods_subtitle = goods_subtitle;
	}
	
	
}
