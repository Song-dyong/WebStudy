package com.sist.dao;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import java.util.List;

public class FundDAO {
	public static void main(String[] args) {
		System.setProperty("webdriver.firefox.driver", "/Users/dyongsong/Desktop/Sele/fire/geckodriver");

		WebDriver driver = new FirefoxDriver();
		FirefoxOptions option = new FirefoxOptions();
		option.addArguments("-headless");
		option.addArguments("--start-maximized");
		option.addArguments("--disable-popup-blocking");

		try {
			String url = "https://www.wadiz.kr/web/store/main";
			driver.get(url);

			List<WebElement> posterElements = null;
			List<WebElement> linkElements = null;

			JavascriptExecutor js = (JavascriptExecutor) driver;
			int k = 1;
			while (true) {
				js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
				Thread.sleep(1000);
				for(int i=k;i<=k+7;i++) {
				WebElement posterElement = driver.findElement(
						By.xpath("/html/body/div[1]/main/div[2]/div[2]/div[4]/div[1]/div[" + i + "]/a/div[1]/div/div"));
				if (posterElement == null) {
					posterElement = driver.findElement(By.xpath(
							"/html/body/div[1]/main/div[2]/div[2]/div[4]/div[1]/div[" + i + "]/a/div[1]/div/div[1]"));
				}
				WebElement linkElement = driver
						.findElement(By.xpath("/html/body/div[1]/main/div[2]/div[2]/div[4]/div[1]/div[" + i + "]/a"));
				
				String poster = posterElement.getAttribute("style");
				String link = linkElement.getAttribute("href");
				System.out.println(k+"----------------------------");
				System.out.println(poster);
				System.out.println(link);
				System.out.println("--------------------------------");
				Thread.sleep(800);
				}
				k+=8;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.quit();
		}
	}

	private static String extractImageUrl(String styleAttribute) {
		int startIndex = styleAttribute.indexOf("url(\"") + 5;
		int endIndex = styleAttribute.indexOf("\")", startIndex);
		return styleAttribute.substring(startIndex, endIndex);
	}
}