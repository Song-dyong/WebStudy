package com.sist.manager;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.sist.dao.*;
import com.sist.vo.ActivityInfoVO;

public class DataCollection {
	public static WebDriver driver;
	public static WebElement element;
	public static String url;

	public static String WEB_DRIVER_ID = "webdriver.firefox.driver";
	public static String WEB_DRIVER_PATH = "/Users/dyongsong/Desktop/Sele/fire/geckodriver.exe";
	
	public DataCollection() {
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

		// 드라이버 옵션 설정
		FirefoxOptions option = new FirefoxOptions();
		option.addArguments("--start-maximized");
		option.addArguments("--disable-popup-blocking");

		driver = new FirefoxDriver(option);
		url = "https://www.myrealtrip.com/offers?t=llp&qct=Jeju&qcr=Korea%2C%20Republic%20of&page=";
	}
	
	public void ActivityInfoData() {
		ActivityDAO dao=ActivityDAO.newInstance();
		try {
			List<ActivityInfoVO> list=new ArrayList<ActivityInfoVO>();
			for (int i = 1; i <= 35; i++) {
				driver.get(url + i);
				Thread.sleep(3000);								
				WebElement title= driver.findElement(By.xpath("/html/body/main/div[3]/div/div[2]/div/article/header/h1"));
				// By.className

				String tag1 = "/html/body/div[4]/div/div/div/div[2]/main/div/div[2]/a[";
				String tag2 = "]/div/div/img";

				for (int j = 1; j <= 24; j++) {
					WebElement imgElement = driver.findElement(By.xpath(tag1 + j + tag2));
					String src = imgElement.getAttribute("src");
					System.out.println(src);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.close();
		}
	}
	
	
	public static void main(String[] args) {
		
	}
}