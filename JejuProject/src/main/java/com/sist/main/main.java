package com.sist.main;

import java.util.Iterator;

public class main {
	public static void main(String[] args) {
		for(int i=1;i<10;i++) {
			if(i==1||i==4) 
				continue;
			System.out.println(i);
		}
	}
}
