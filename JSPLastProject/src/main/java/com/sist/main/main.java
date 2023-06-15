package com.sist.main;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
 
public class main {
    private WebDriver driver;
    private WebElement element;
    private String url;
    
    public static String WEB_DRIVER_ID = "webdriver.firefox.driver";
	public static String WEB_DRIVER_PATH = "C:\\Users\\SIST\\Desktop\\Sele\\fire\\geckodriver.exe";
	
	public main() {
		// 웹드라이버 경로 설정
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
		
		// 드라이버 옵션 설정
		FirefoxOptions option= new FirefoxOptions();
		option.addArguments("--start-maximized");
		option.addArguments("--disable-popup-blocking");
		
		driver = new FirefoxDriver(option);
		url="https://visitjeju.net/kr/detail/list?menuId=DOM_000001718000000000&cate1cd=cate0000000002#p1&pageSize=12&sortListType=reviewcnt&viewType=map&isShowBtag&tag";
	}

	public void dataGet() {
		try {
			driver.get(url);
			Thread.sleep(10000);
			
			String tag1="/html/body/div/div[2]/div/div[2]/div/div/div/div[2]/div[5]/div[2]/div/ul/li[1]/dl/dt/a/p[1]\r\n";
			
			// By.className
			
			WebElement Element= driver.findElement(By.xpath(tag1));
			
			System.out.println(Element);
			
			System.out.println("끝");
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			driver.close();
		}
	}
	
    public static void main(String[] args) {
    	main m = new main();
    	m.dataGet();
    }	
}
