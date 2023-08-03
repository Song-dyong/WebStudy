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
	public static String WEB_DRIVER_PATH = "/Users/dyongsong/Desktop/Sele/fire/geckodriver.exe";

	public DataCollection2() {
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

		// 드라이버 옵션 설정
		FirefoxOptions option = new FirefoxOptions();
		option.addArguments("--start-maximized");
		option.addArguments("--disable-popup-blocking");
		option.addArguments("-headless");

		driver = new FirefoxDriver(option);
		url = "https://www.myrealtrip.com/offers?t=llp&qct=Jeju&qcr=Korea%2C%20Republic%20of&ext_categories=";
	}

	public void ActivityInfoData() {
		ActivityDAO dao = ActivityDAO.newInstance();
		try {
			List<ActivityCategoryVO> list = dao.activityCategoryData();
			int count = 0;
			for (ActivityCategoryVO vo : list) {

				driver.get(url + vo.getLink()+"&page=2");
				Thread.sleep(1000);
				// linkElements => 카테고리의 페이지별 24개의 카드 url

				List<WebElement> linkElements = driver
						.findElements(By.xpath("/html/body/div[4]/div/div/div/div[2]/main/div/div[2]/a"));
				
				int linkCount = linkElements.size();
				for (int i = 0; i < linkCount; i++) {
					try {
						linkElements = driver
								.findElements(By.xpath("/html/body/div[4]/div/div/div/div[2]/main/div/div[2]/a"));
						ActivityInfoVO avo = new ActivityInfoVO();
						avo.setAccno(vo.getAccno());
						// "/html/body/div[4]/div/div/div/div[2]/main/div/div[2]/a["+i+"]/div/div/img"

						WebElement posterLink = findElementWithFallback(driver, By.xpath(
								"/html/body/div[4]/div/div/div/div[2]/main/div/div[2]/a[" + (i + 1) + "]/div/div/img"));
						String posterLink_src = null;
						if (posterLink != null) {
							posterLink_src = posterLink.getAttribute("src");
							posterLink_src = posterLink_src.replace("&", "#");
						} else {
							posterLink_src = "no";
						}
						avo.setMain_poster(posterLink_src);

						// 메인 포스터 변수 하나 잡기
						// avo.setLocation_poster(posterLink_src != null ? posterLink_src : "ㅋㅋㅋㅋ");

						WebElement linkElement = linkElements.get(i);
						String link = linkElement.getAttribute("href");
						driver.get(link);
						// 24개 카드 url 연결
						// 저장할 infoVO 생성
						// 카테고리 번호는 카테고리VO에서 참조
						// 타이틀 태그
						WebElement title = findElementWithFallback(driver,
								By.xpath("/html/body/main/div[3]/div/div[2]/div/article/header/h1"));
						avo.setTitle(title != null ? title.getText() : "no");
						if (avo.getTitle().equals("no")) {
							System.out.println("제목이 없어요!!!");
							driver.navigate().back(); // 이전 페이지로 돌아감
							Thread.sleep(1000);
							continue; // 다음 반복으로 진행
						}

						StringBuilder imageUrls = new StringBuilder();
//				/html/body/main/div[3]/div/div[2]/div/article/section[2]/div[2]/div/div/div[1]/img[1]
						List<WebElement> images1 = driver.findElements(By.xpath(
							  ///html/body/main/div[3]/div/div[2]/div/article/section[2]/div[2]/div/div/div[1]
								"/html/body/main/div[3]/div/div[2]/div/article/section[2]/div[2]/div/div/div[1]/img"));
						for (int j = 0; j < images1.size(); j++) {
							WebElement imageElement = images1.get(j);
							String imageSrc = imageElement.getAttribute("src");
							imageUrls.append(imageSrc);

							if (j < images1.size() - 1) {
								imageUrls.append("^");

							}
						}

						List<WebElement> images2 = driver.findElements(By.xpath(
								"/html/body/main/div[3]/div/div[2]/div/article/section[2]/div/div/div/div[1]/img"));
						for (int j = 0; j < images2.size(); j++) {
							WebElement imageElement = images2.get(j);
							String imageSrc = imageElement.getAttribute("src");
							if (imageUrls.length() > 0) {
								imageUrls.append("^");
							}
							imageUrls.append(imageSrc);
						}

						String imageUrlsStr = imageUrls.toString();
						avo.setPoster(imageUrlsStr.replace("&", "#"));

						// 평점 태그
						WebElement score = findElementWithFallback(driver, By.xpath(
								"/html/body/main/div[3]/div/div[2]/div/article/header/div[2]/div/button/span[1]"));
						avo.setScore(score != null ? Double.parseDouble(score.getText()) : 0.0);
						// 리뷰 갯수
						WebElement review_count = findElementWithFallback(driver, By.xpath(
								"/html/body/main/div[3]/div/div[2]/div/article/header/div[2]/div/button/span[2]"));
						avo.setReview_count(review_count != null ? review_count.getText() : "no");
						// 가격 태그
						WebElement price = findElementWithFallback(driver, By.xpath(
								"/html/body/main/div[3]/div/div[2]/div/aside/ul/li[1]/div/div[1]/div[1]/div[1]/div[2]/strong"));
						if (price != null) {
							if (Integer.parseInt(price.getText().replace(",", "").replaceAll("원", "")) == 0) {
								price = findElementWithFallback(driver, By.xpath(
										"/html/body/main/div[3]/div/div[2]/div/aside/ul/li[1]/div/div[1]/div[1]/div[1]/div/strong"));
							}
						} else {
							price = findElementWithFallback(driver, By.xpath(
									"/html/body/main/div[3]/div/div[2]/div/aside/ul/li[1]/div/div[1]/div[1]/div[1]/div/strong"));
						}
						avo.setPrice(
								price != null ? Integer.parseInt(price.getText().replace(",", "").replaceAll("원", ""))
										: 0);

						WebElement reviewer = findElementWithFallback(driver, By.xpath(
								"/html/body/main/div[3]/div/div[2]/div/article/section[3]/div/div/div/div[2]/p"));
						avo.setReviewer(reviewer != null ? reviewer.getText() : "no");

						WebElement review_content = findElementWithFallback(driver, By.xpath(
								"/html/body/main/div[3]/div/div[2]/div/article/section[3]/div/div/div/div[2]/div[3]/div[1]"));
						avo.setReview_content(review_content != null ? review_content.getText() : "no");

						WebElement hours_use = findElementWithFallback(driver,
								By.xpath("/html/body/main/div[3]/div/div[2]/div/article/section[6]/div/div/div[1]/p"));
						if (hours_use == null) {
							hours_use = findElementWithFallback(driver, By.xpath(
									"/html/body/main/div[3]/div/div[2]/div/article/section[5]/div/div/div[1]/p"));
							if (hours_use == null) {
								hours_use = findElementWithFallback(driver, By.xpath(
										"/html/body/main/div[3]/div/div[2]/div/article/section[7]/div/div/div[1]/p"));
							}
						}
						avo.setHours_use(hours_use != null ? hours_use.getText() : "no");

						WebElement location_name = findElementWithFallback(driver, By.xpath(
								"/html/body/main/div[3]/div/div[2]/div/article/section[6]/div/div/div[2]/div/p"));
						if (location_name == null) {
							location_name = findElementWithFallback(driver, By.xpath(
									"/html/body/main/div[3]/div/div[2]/div/article/section[5]/div/div/div[2]/div/p"));
							if (location_name == null) {
								location_name = findElementWithFallback(driver, By.xpath(
										"/html/body/main/div[3]/div/div[2]/div/article/section[7]/div/div/div[2]/div/p"));
							}
						}
						avo.setLocation_name(location_name != null ? location_name.getText() : "no");

						WebElement location_poster = findElementWithFallback(driver, By.xpath(
								"/html/body/main/div[3]/div/div[2]/div/article/section[6]/div/div/div[2]/div/div/a/img"));
						if (location_poster == null) {
							location_poster = findElementWithFallback(driver, By.xpath(
									"/html/body/main/div[3]/div/div[2]/div/article/section[5]/div/div/div[2]/div/div/a/img"));
							if (location_poster == null) {
								location_poster = findElementWithFallback(driver, By.xpath(
										"/html/body/main/div[3]/div/div[2]/div/article/section[7]/div/div/div[2]/div/div/a[1]/img"));
							}
						}
						String location_poster_src = null;
						if (location_poster != null) {
							location_poster_src = location_poster.getAttribute("src");
							location_poster_src = location_poster_src.replace("&", "#");
						}
						avo.setLocation_poster(location_poster_src != null ? location_poster_src : "no");

						WebElement how_use = findElementWithFallback(driver, By.xpath(
								"/html/body/main/div[3]/div/div[2]/div/article/section[6]/div/div/div[3]/div/div[1]/p"));
						if (how_use == null) {
							how_use = findElementWithFallback(driver, By.xpath(
									"/html/body/main/div[3]/div/div[2]/div/article/section[5]/div/div/div[3]/div/div[1]/p"));
							if (how_use == null) {
								how_use = findElementWithFallback(driver, By.xpath(
										"/html/body/main/div[3]/div/div[2]/div/article/section[7]/div/div/div[3]/div/div[1]/p"));
							}
						}
						avo.setHow_use(how_use != null ? how_use.getText() : "no");

						System.out.println(i + "번째 게시물");
						System.out.println("제목: " + avo.getTitle());
						// System.out.println("평점: " + avo.getScore());
						// System.out.println("리뷰갯수: " + avo.getReview_count());
						// System.out.println("가격: " + avo.getPrice());
						// System.out.println("리뷰어: " + avo.getReviewer());
						// System.out.println("리뷰내용: " + avo.getReview_content());
						// System.out.println("이용시간: " + avo.getHours_use());
						// System.out.println("위치주소: " + avo.getLocation_name());
						// System.out.println("위치지도: " + avo.getLocation_poster());
						// System.out.println("이용방법: " + avo.getHow_use());
						// System.out.println("포스터: " + avo.getPoster());
						System.out.println("메인포스터: " + avo.getMain_poster());
						System.out.println("총 갯수: " + count);
						//dao.activityInfoInsert(avo);
						count++;
						System.out.println("-------------------------------------------------------------------------");
						driver.navigate().back(); // 이전 페이지로 돌아감
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		} catch (

		Exception e) {
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
		DataCollection2 d = new DataCollection2();
		d.ActivityInfoData();
	}

}
