package com.sist.manager;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.sist.dao.*;
import com.sist.vo.ActivityCategoryVO;
import com.sist.vo.ActivityInfoVO;

public class DataCollection2 {
	public static WebDriver driver;
	public static WebElement element;
	public static String url;

	public static String WEB_DRIVER_ID = "webdriver.firefox.driver";
	public static String WEB_DRIVER_PATH = "C:\\Users\\SIST\\Desktop\\Sele\\fire\\geckodriver.exe";
	
	public DataCollection2() {
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

		// 드라이버 옵션 설정
		FirefoxOptions option = new FirefoxOptions();
		option.addArguments("--start-maximized");
		option.addArguments("--disable-popup-blocking");
		option.addArguments("-headless");

		driver = new FirefoxDriver(option);
		url = "https://www.myrealtrip.com/offers?t=llp&qct=Jeju&qcr=Korea%2C%20Republic%20of&ext_categories=";
		//https://www.myrealtrip.com/offers?t=llp&qct=Jeju&qcr=Korea%2C%20Republic%20of&ext_categories=activity&page=2
	}
	
	public void ActivityInfoData() {
	    ActivityDAO dao = ActivityDAO.newInstance();
	    try {
	        List<ActivityCategoryVO> list = dao.activityCategoryData();

	        for (ActivityCategoryVO vo : list) {
	            driver.get(url + vo.getLink());
	            Thread.sleep(3000);
	            // linkElements => 카테고리의 페이지별 24개의 카드 url
	            List<WebElement> linkElements = driver.findElements(By.xpath("/html/body/div[4]/div/div/div/div[2]/main/div/div[2]/a"));

	            int linkCount = linkElements.size();
	            	
	            for (int i = 0; i < linkCount; i++) {
	            	
	                linkElements = driver.findElements(By.xpath("/html/body/div[4]/div/div/div/div[2]/main/div/div[2]/a"));
	                
	                WebElement linkElement = linkElements.get(i);
	                String link = linkElement.getAttribute("href");
	                driver.get(link);
	                // 24개 카드 url 연결
	                // 저장할 infoVO 생성
	                ActivityInfoVO avo = new ActivityInfoVO();
	                avo.setAccno(vo.getAccno());
	                // 카테고리 번호는 카테고리VO에서 참조
	                // 타이틀 태그													
	                WebElement title = findElementWithFallback(driver, By.xpath("/html/body/main/div[3]/div/div[2]/div/article/header/h1"));
	                avo.setTitle(title != null ? title.getText() : "ㅋㅋㅋㅋ");
	                if (avo.getTitle().equals("ㅋㅋㅋㅋ")) {
	                    System.out.println("Title is null. Skipping to next page.");
	                    driver.navigate().back(); // 이전 페이지로 돌아감
	                    Thread.sleep(1000);
	                    continue; // 다음 반복으로 진행
	                }
	                // 평점 태그
	                WebElement score = findElementWithFallback(driver, By.xpath("/html/body/main/div[3]/div/div[2]/div/article/header/div[2]/div/button/span[1]"));
	                avo.setScore(score != null ? Double.parseDouble(score.getText()) : 0.0);
	                // 리뷰 갯수
	                WebElement review_count = findElementWithFallback(driver, By.xpath("/html/body/main/div[3]/div/div[2]/div/article/header/div[2]/div/button/span[2]"));
	                avo.setReview_count(review_count != null ? review_count.getText() : "ㅋㅋㅋㅋ");
	                // 가격 태그													
	                WebElement price = findElementWithFallback(driver, By.xpath("/html/body/main/div[3]/div/div[2]/div/aside/ul/li[1]/div/div[1]/div[1]/div[1]/div[2]/strong"));
	                if (price != null) {
	                    if (Integer.parseInt(price.getText().replace(",", "").replaceAll("원", "")) == 0) {
	                        price = findElementWithFallback(driver, By.xpath("/html/body/main/div[3]/div/div[2]/div/aside/ul/li[1]/div/div[1]/div[1]/div[1]/div/strong"));
	                    }
	                } else {
	                    price = findElementWithFallback(driver, By.xpath("/html/body/main/div[3]/div/div[2]/div/aside/ul/li[1]/div/div[1]/div[1]/div[1]/div/strong"));
	                }
	                avo.setPrice(price != null ? Integer.parseInt(price.getText().replace(",", "").replaceAll("원", "")) : 0);

	                WebElement reviewer = findElementWithFallback(driver, By.xpath("/html/body/main/div[3]/div/div[2]/div/article/section[3]/div/div/div/div[2]/p"));
	                avo.setReviewer(reviewer != null ? reviewer.getText() : "ㅋㅋㅋㅋ");
	                
	                WebElement review_content = findElementWithFallback(driver, By.xpath("/html/body/main/div[3]/div/div[2]/div/article/section[3]/div/div/div/div[2]/div[3]/div[1]"));
	                avo.setReview_content(review_content != null ? review_content.getText() : "ㅋㅋㅋㅋ");
	                
	                WebElement hours_use = findElementWithFallback(driver, By.xpath("/html/body/main/div[3]/div/div[2]/div/article/section[6]/div/div/div[1]/p"));
	                if(hours_use==null) {
	                	hours_use = findElementWithFallback(driver, By.xpath("/html/body/main/div[3]/div/div[2]/div/article/section[5]/div/div/div[1]/p"));
	                	if(hours_use==null) {							
	                		hours_use = findElementWithFallback(driver, By.xpath("/html/body/main/div[3]/div/div[2]/div/article/section[7]/div/div/div[1]/p"));
	                	}
	                }
	                avo.setHours_use(hours_use != null ? hours_use.getText() : "ㅋㅋㅋㅋ");
	                
	                WebElement location_name = findElementWithFallback(driver, By.xpath("/html/body/main/div[3]/div/div[2]/div/article/section[6]/div/div/div[2]/div/p"));
	                if(location_name==null) {
	                	location_name = findElementWithFallback(driver, By.xpath("/html/body/main/div[3]/div/div[2]/div/article/section[5]/div/div/div[2]/div/p"));
	                	if(location_name==null) {
	                		location_name = findElementWithFallback(driver, By.xpath("/html/body/main/div[3]/div/div[2]/div/article/section[7]/div/div/div[2]/div/p"));
	                	}
	                }
	                avo.setLocation_name(location_name != null ? location_name.getText() : "ㅋㅋㅋㅋ");
	                
// 포스터 이미지 태그로 바꾸기	       /html/body/main/div[3]/div/div[2]/div/article/section[7]/div/div/div[2]/div/div/a[1]/img															         
	                WebElement location_poster = findElementWithFallback(driver, By.xpath("/html/body/main/div[3]/div/div[2]/div/article/section[6]/div/div/div[2]/div/div/a/img"));
	                if(location_poster==null) {
	                	location_poster=findElementWithFallback(driver, By.xpath("/html/body/main/div[3]/div/div[2]/div/article/section[5]/div/div/div[2]/div/div/a/img"));
	                	if(location_poster==null) {
		                	location_poster=findElementWithFallback(driver, By.xpath("/html/body/main/div[3]/div/div[2]/div/article/section[7]/div/div/div[2]/div/div/a[1]/img	"));
		                }
	                }
	                String location_poster_src = null;
	                if (location_poster != null) {
	                    location_poster_src = location_poster.getAttribute("src");
	                }
	                avo.setLocation_poster(location_poster_src != null ? location_poster_src : "ㅋㅋㅋㅋ");
	                
	                WebElement how_use = findElementWithFallback(driver, By.xpath("/html/body/main/div[3]/div/div[2]/div/article/section[6]/div/div/div[3]/div/div[1]/p"));
	                if(how_use==null) {
	                	how_use = findElementWithFallback(driver, By.xpath("/html/body/main/div[3]/div/div[2]/div/article/section[5]/div/div/div[3]/div/div[1]/p"));
	                	if(how_use==null) {
	                		how_use = findElementWithFallback(driver, By.xpath("/html/body/main/div[3]/div/div[2]/div/article/section[7]/div/div/div[3]/div/div[1]/p"));
	                	}
	                }
	                avo.setHow_use(how_use != null ? how_use.getText() : "ㅋㅋㅋㅋ");
	                
// poster 여러 장 ^로 구분해서 저장하기	                
//	                WebElement poster = findElementWithFallback(driver, By.xpath("/html/body/main/div[3]/div/div[2]/div/article/section[2]/div[2]/div/div/div[1]/img[1]"));
//	                avo.setPoster(poster != null ? poster.getText() : "");
	                //														 /html/body/main/div[3]/div/div[2]/div/article/div[2]/div/div/div/div/ul/li[1]/div/div[2]
	                												//		 /html/body/div[17]/div/div/div[2]/div/div[1]/div/div[12]/img
	                StringBuilder imageUrls = new StringBuilder();

	             // 이미지 태그 1 처리
	             List<WebElement> images1 = driver.findElements(By.xpath("/html/body/main/div[3]/div/div[2]/div/article/section[2]/div[2]/div/div/div[1]/img"));
	             for ( i = 0; i < images1.size(); i++) {
	                 WebElement imageElement = images1.get(i);
	                 String imageSrc = imageElement.getAttribute("src");
	                 imageUrls.append(imageSrc);

	                 if (i < images1.size() - 1) {
	                     imageUrls.append("^");
	                 }
	             }

	             // 이미지 태그 2 처리
	             List<WebElement> images2 = driver.findElements(By.xpath("/html/body/div[17]/div/div/div[2]/div/div[1]/div/div/img"));
	             for ( i = 0; i < images2.size(); i++) {
	                 WebElement imageElement = images2.get(i);
	                 String imageSrc = imageElement.getAttribute("src");
	                 if (imageUrls.length() > 0) {
	                     imageUrls.append("^");
	                 }
	                 imageUrls.append(imageSrc);
	             }

	             // 이미지 태그 3 처리
	             List<WebElement> images3 = driver.findElements(By.xpath("/html/body/main/div[3]/div/div[2]/div/article/section[2]/div/div/div/div[1]/img"));
	             for ( i = 0; i < images3.size(); i++) {
	                 WebElement imageElement = images3.get(i);
	                 String imageSrc = imageElement.getAttribute("src");
	                 if (imageUrls.length() > 0) {
	                     imageUrls.append("^");
	                 }
	                 imageUrls.append(imageSrc);
	             }

	             // 이미지 태그 4 처리
	             List<WebElement> images4 = driver.findElements(By.xpath("/html/body/main/div[3]/div/div[2]/div/article/section[2]/div[2]/div/div/div[1]/img"));
	             for ( i = 0; i < images4.size(); i++) {
	                 WebElement imageElement = images4.get(i);
	                 String imageSrc = imageElement.getAttribute("src");
	                 if (imageUrls.length() > 0) {
	                     imageUrls.append("^");
	                 }
	                 imageUrls.append(imageSrc);
	             }

	             // 이미지 태그 5 처리
	             List<WebElement> images5 = driver.findElements(By.xpath("/html/body/div[14]/div/div/div[2]/div/div[1]/div/div/img"));
	             for ( i = 0; i < images5.size(); i++) {
	                 WebElement imageElement = images5.get(i);
	                 String imageSrc = imageElement.getAttribute("src");
	                 if (imageUrls.length() > 0) {
	                     imageUrls.append("^");
	                 }
	                 imageUrls.append(imageSrc);
	             }

	             // 이미지 태그 6 처리
	             List<WebElement> images6 = driver.findElements(By.xpath("/html/body/main/div[3]/div/div[2]/div/article/section[2]/div[2]/div/div/div[1]/img"));
	             for ( i = 0; i < images6.size(); i++) {
	                 WebElement imageElement = images6.get(i);
	                 String imageSrc = imageElement.getAttribute("src");
	                 if (imageUrls.length() > 0) {
	                     imageUrls.append("^");
	                 }
	                 imageUrls.append(imageSrc);
	             }

	             String imageUrlsStr = imageUrls.toString();
	             avo.setPoster(imageUrlsStr);


	                
	                
	                System.out.println(i+"번째 게시물");
	                System.out.println("제목: "+avo.getTitle());
	                System.out.println("평점: " + avo.getScore());
	                System.out.println("리뷰갯수: "+avo.getReview_count());
	                System.out.println("가격: "+avo.getPrice());
	                System.out.println("리뷰어: "+avo.getReviewer());
	                System.out.println("리뷰내용: "+avo.getReview_content());
	                System.out.println("이용시간: "+avo.getHours_use());
	                System.out.println("위치주소: "+avo.getLocation_name());
	                System.out.println("위치지도: "+avo.getLocation_poster());
	                System.out.println("이용방법: "+avo.getHow_use());
	                System.out.println("포스터: "+avo.getPoster());
	                System.out.println("-------------------------------------------------------------------------");
	                driver.navigate().back(); // 이전 페이지로 돌아감
	                Thread.sleep(3000);
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        driver.close();
	    }
	}

	/*
	 * private WebElement findElementWithFallback(WebDriver driver, By by) { try {
	 * return driver.findElement(by); } catch (NoSuchElementException e) { return
	 * null; } }
	 */

	private WebElement findElementWithFallback(WebDriver driver, By by) {
	    try {
	        return driver.findElement(by);
	    } catch (NoSuchElementException e) {
	        return null;
	    } catch (StaleElementReferenceException e) {
	        // 게시물 번호에 따라 XPath가 변경되는 경우 다시 시도
	        List<WebElement> elements = driver.findElements(by);
	        if (elements.size() > 0) {
	            return elements.get(0);
	        } else {
	            return null;
	        }
	    }
	}


	
	public static void main(String[] args) {
		DataCollection2 d=new DataCollection2();
		d.ActivityInfoData();
	}
	
}
