package com.sist.vo;

/*
	mno NUMBER,
	cno NUMBER,
	title VARCHAR2(300) CONSTRAINT md_title_nn NOT NULL,			--제목
	poster VARCHAR2(300) CONSTRAINT md_poster_nn NOT NULL,			--포스터
	artist VARCHAR2(300) CONSTRAINT md_artist_nn NOT NULL,			--가수
	alname VARCHAR2(300) CONSTRAINT md_alname_nn NOT NULL,			--앨범명
	genre VARCHAR2(300) CONSTRAINT md_genre_nn NOT NULL,			--장르
	time VARCHAR2(300) CONSTRAINT md_time_nn NOT NULL,				--재생시간
	lyricist VARCHAR2(300),											--작사가
	composer VARCHAR2(300),											--작곡가
	lyrics CLOB,													--가사


*/
public class MusicVO {
	private int mno, cno;
	private String title, poster, artist, alname, genre, time, lyricist, composer, lyrics;
	
	public int getMno() {
		return mno;
	}
	public void setMno(int mno) {
		this.mno = mno;
	}
	public int getCno() {
		return cno;
	}
	public void setCno(int cno) {
		this.cno = cno;
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
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getAlname() {
		return alname;
	}
	public void setAlname(String alname) {
		this.alname = alname;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLyricist() {
		return lyricist;
	}
	public void setLyricist(String lyricist) {
		this.lyricist = lyricist;
	}
	public String getComposer() {
		return composer;
	}
	public void setComposer(String composer) {
		this.composer = composer;
	}
	public String getLyrics() {
		return lyrics;
	}
	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}
	
}
