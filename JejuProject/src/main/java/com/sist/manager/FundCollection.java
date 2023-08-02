package com.sist.manager;

import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.sist.vo.FundVO;

public class FundCollection {
	public static WebDriver driver;
	public static WebElement element;
	public static String url;

	public static String WEB_DRIVER_ID = "webdriver.firefox.driver";
	public static String WEB_DRIVER_PATH = "/Users/dyongsong/Desktop/Sele/fire/geckodriver.exe";

	public FundCollection() {
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

		// 드라이버 옵션 설정
		FirefoxOptions option = new FirefoxOptions();
		option.addArguments("--start-maximized");
		option.addArguments("--disable-popup-blocking");
		option.addArguments("-headless");

		driver = new FirefoxDriver(option);
		url = "https://www.wadiz.kr/web/wreward/main";
	}

	public void fundData() {
//		List<FundVO> list=new ArrayList<FundVO>();
		try {
			int count = 1;

			driver.get(url);
			
			// linkElements => 카테고리의 페이지별 24개의 카드 url
			

			for (int i = 1; i <= 24; i++) {
				try {
					FundVO vo=new FundVO();
					vo.setFno(i);
					Thread.sleep(3000);
					
					WebElement title = driver
							.findElement(By.xpath("/html/body/div[1]/main/div[2]/div[6]/div[2]/div["+i+"]/a/div[2]/div[2]"));
					vo.setTitle(title.getText());
					
					WebElement poster = driver.findElement(By.xpath("/html/body/div[1]/main/div[2]/div[6]/div[2]/div["+i+"]/a/div[1]/div"));
					String posterUrl = poster.getAttribute("style");
					
					vo.setMain_poster(posterUrl);
					
					WebElement link = driver.findElement(By.xpath("/html/body/div[1]/main/div[2]/div[6]/div[2]/div["+i+"]/a "));
					
					String alink = link.getAttribute("href");
					vo.setLink(alink);
					
					WebElement crowd_money = driver
							.findElement(By.xpath("/html/body/div[1]/main/div[2]/div[6]/div[2]/div["+i+"]/a/div[2]/div[1]/div[1]/p[2]"));
					vo.setCrowd_money(crowd_money.getText());

					System.out.println(i + "번째 게시물");
					System.out.println("제목: " + vo.getTitle());
					System.out.println("메인포스터: " + vo.getMain_poster());
					System.out.println(i);
					System.out.println("상세 링크: " + vo.getLink());
					System.out.println("모금액: " + vo.getCrowd_money());
					System.out.println("총 갯수: " + count);
					count++;
					System.out.println("-------------------------------------------------------------------------");
					Thread.sleep(3000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.close();
		}
	}
	public static void main(String[] args) {
		FundCollection f=new FundCollection();
		f.fundData();
	}

}
