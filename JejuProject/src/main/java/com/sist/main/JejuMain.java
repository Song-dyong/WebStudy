package com.sist.main;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JejuMain {
	 public void CourseDetailData()
	    {
	       
	            String[] id = {"160609000007", "160613000008", "160612000002", "160612000003", "151007000003", "151007000014", "151006000007", "151006000008", "151006000009", "151006000010", "151006000011", "151006000012"};
	            StringBuilder sb=new StringBuilder();
	            for (int i = 0; i < id.length; i++) {
	                try {
	                    Document doc = Jsoup.connect("https://planner.jeju.com/plans/plan_view.html?agt=jeju&pl_no=" + id[i]).get();
	                    Elements containers = doc.select(".pl-vertical-container.pl-light-timeline.pl-center-orientation");
	                    int dayCount = 1;
	                    for (Element container : containers) 
	                    {
	                        Elements titles = container.select(".btn-pl-product-detail");
	                        System.out.println("\nDay " + dayCount);
	                        for (Element title : titles) {
	                            String courseTitle = title.text();
	                            if (courseTitle.contains("렌터카")) {
	                                System.out.println("렌터카 (렌터카페이지로 이동하기)");
	                                
	                            } else if(!courseTitle.equals("")){
//	                               courseTitle=sb.append(courseTitle.substring(courseTitle.indexOf("]")+2)).toString();
//	                               courseTitle=sb.append(courseTitle.substring(0,courseTitle.lastIndexOf(" "))).toString();
//	                               courseTitle=sb.append(courseTitle.replaceAll("입장권 - ", "")).toString();
//	                               courseTitle=sb.append(courseTitle.replaceAll("입장 - ", "")).toString();
//	                               courseTitle=sb.append(courseTitle.replaceAll("제주", "")).toString();
//	                               courseTitle=sb.append(courseTitle.replaceAll("서귀포", "")).toString();
//	                               courseTitle=sb.append(courseTitle.replaceAll("시", "")).toString();
	                               if(courseTitle.contains("공항"))
	                            	  courseTitle=sb.append(courseTitle.substring(0, courseTitle.indexOf("항")+1)).toString();
	                               
	                               // System.out.println(courseTitle);
	                               
	                            }
	                            
	                        }
	                        dayCount++;
	                    }
	                } catch (Exception e) { break;}
	            }
	    }
	 public static void main(String[] args) {
		JejuMain j=new JejuMain();
		j.CourseDetailData();
	}
}
