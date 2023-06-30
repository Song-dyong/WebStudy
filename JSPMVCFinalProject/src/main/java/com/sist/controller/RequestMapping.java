package com.sist.controller;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
/*
	@ => Type
	class class_name{
	
		@ => Field
		private A a;
		
		@ => Constructor
		public class_name(){
		
		}
		
		@ => Method
		public void display(){
		
		}
	}
	
	RequestMapping이라는 어노테이션을 통해 해당 메소드를 찾으려해도, 구분자가 필요함
	
		@RequestMapping("list.do")
		public void display1(){
		
		}
		@RequestMapping
		public void display2(){
		
		}
		@RequestMapping
		public void display3(){
		
		}
		@RequestMapping
		public void display4(){
		
		}
		@RequestMapping
		public void display5(){
		
		}	
	


*/
public @interface RequestMapping {
	   public String value();
	}