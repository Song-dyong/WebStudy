package com.sist.dao;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sist.vo.*;

import java.time.Duration;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StoreInsert {
	public static void main(String[] args) {
		StoreDAO dao = StoreDAO.newInstance();
		System.setProperty("webdriver.firefox.driver", "/Users/dyongsong/Desktop/Sele/fire/geckodriver");

		WebDriver driver = new FirefoxDriver();
		FirefoxOptions option = new FirefoxOptions();
		option.addArguments("-headless");
		List<LinkVO> list = dao.getLinkPosterList();
		try {
			for (LinkVO avo : list) {
				StoreDetailVO vo = new StoreDetailVO();
				driver.get(avo.getLink());
				Thread.sleep(2000);
				/*
				 * detail_poster varchar2(3000), detail_intro CLOB => 없음. scno NUMBER => 카테고리
				 * 이름에 따라 번호 지정 (if) score number(2,1) => 테이블 수정
				 */

				// vo.setMain_poster(avo.getMain_poster());
				System.out.println(avo.getLink());
				WebElement goods_title = driver.findElement(By.className("DetailInfoHeader_title__i0kaY"));
				String title = goods_title.getText();
				System.out.println(title);

				WebElement parti_count = driver.findElement(By.className("DetailInfoHeader_text__2P224"));
				String partiStr = parti_count.getText();
				int partiInt = Integer.parseInt(partiStr.replace(",", ""));
				System.out.println(partiInt);
				// vo.setParti_count(partiInt);
				// 태그 없는 제품 예외처리
				WebElement tag = driver.findElement(By.xpath(
						"/html/body/div[1]/main/div[2]/section/div/div[1]/div[2]/div/div[1]/div[1]/div[1]/div/a"));
				String strtag = tag.getAttribute("data-ga-label");
				System.out.println(strtag);
				// vo.setTag(strtag);
				// ProductFloatButton_count__1a-7B 1만+ 예외처리
				WebElement jjim_count = driver.findElement(By.className("ProductFloatButton_count__1a-7B"));
				String jjimStr = jjim_count.getText().replace(",", "");
				System.out.println(jjimStr);
				if (jjimStr.equals("1만+"))
					jjimStr = "10000";
				int jjimInt = Integer.parseInt(jjimStr);
				// vo.setJjim_count(jjimInt);

				StringBuilder imageUrls = new StringBuilder();
				List<WebElement> images1 = driver.findElements(By.xpath(
						"/html/body/div[1]/main/div[2]/section/div/div[2]/div/div/div[1]/div/div[3]/div[1]/div/div[1]/p/img"));
				if(images1.size()==1||images1.size()==0) {
					images1 = driver.findElements
							(By.xpath("/html/body/div[1]/main/div[2]/section/div/div[2]/div/div/div[1]/div/div[3]/div[1]/div/div[1]/p/strong/img"));
				}
				// /html/body/div[1]/main/div[2]/section/div/div[2]/div/div/div[1]/div/div[4]/div[1]/div/div[1]/p[3]/img
//				if(images1.size()==1||images1.size()==0) {
//					images1 = driver.findElements
//							(By.xpath("/html/body/div[1]/main/div[2]/section/div/div[2]/div/div/div[1]/div/div[4]/div[1]/div/div[1]/p/img"));
//				}
				System.out.println(images1.size());
				for (int j = 0; j < images1.size(); j++) {
					WebElement imageElement = images1.get(j);
					String imageSrc = imageElement.getAttribute("data-src");
					imageUrls.append(imageSrc);

					if (j < images1.size() - 1) {
						imageUrls.append("^");
					}
				}
				String imageUrlsStr = imageUrls.toString();
				System.out.println(imageUrlsStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.quit();
		}
	}
}