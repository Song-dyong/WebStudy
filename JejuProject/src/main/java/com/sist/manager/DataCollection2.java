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

	            List<WebElement> linkElements = driver.findElements(By.xpath("/html/body/div[4]/div/div/div/div[2]/main/div/div[2]/a"));

	            int linkCount = linkElements.size();

	            for (int i = 0; i < linkCount; i++) {
	                linkElements = driver.findElements(By.xpath("/html/body/div[4]/div/div/div/div[2]/main/div/div[2]/a"));

	                WebElement linkElement = linkElements.get(i);
	                String link = linkElement.getAttribute("href");
	                driver.get(link);

	                ActivityInfoVO avo = new ActivityInfoVO();
	                avo.setAccno(vo.getAccno());

	                WebElement title = findElementWithFallback(driver, By.xpath("/html/body/main/div[3]/div/div[2]/div/article/header/h1"));
	                avo.setTitle(title != null ? title.getText() : "");

	                WebElement score = findElementWithFallback(driver, By.xpath("/html/body/main/div[3]/div/div[2]/div/article/header/div[2]/div/button/span[1]"));
	                avo.setScore(score != null ? Double.parseDouble(score.getText()) : 0.0);

	                WebElement review_count = findElementWithFallback(driver, By.xpath("/html/body/main/div[3]/div/div[2]/div/article/header/div[2]/div/button/span[2]"));
	                avo.setReview_count(review_count != null ? review_count.getText() : "");
	                															// /html/body/main/div[3]/div/div[2]/div/aside/ul/li[1]/div/div[1]/div[1]/div[1]/div/strong
	                WebElement price = findElementWithFallback(driver, By.xpath("/html/body/main/div[3]/div/div[2]/div/aside/ul/li[1]/div/div[1]/div[1]/div[1]/div[2]/strong"));
	                avo.setPrice(price != null ? Integer.parseInt(price.getText().replace(",", "").replaceAll("원", "")) : 0);

	                WebElement discount_rate = findElementWithFallback(driver, By.xpath("/html/body/main/div[3]/div/div[2]/div/aside/ul/li[1]/div/div[1]/div[1]/div[1]/div[1]/span[2]"));
	                avo.setDiscount_rate(discount_rate != null ? discount_rate.getText() : "");

	                System.out.println(avo.getTitle());
	                System.out.println(avo.getScore());
	                System.out.println(avo.getReview_count());
	                System.out.println(avo.getPrice());
	                System.out.println(avo.getDiscount_rate());

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

	private WebElement findElementWithFallback(WebDriver driver, By by) {
	    try {
	        return driver.findElement(by);
	    } catch (NoSuchElementException e) {
	        return null;
	    }
	}




	
	public static void main(String[] args) {
		DataCollection2 d=new DataCollection2();
		d.ActivityInfoData();
	}
	
}
