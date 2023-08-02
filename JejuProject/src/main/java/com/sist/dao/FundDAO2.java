package com.sist.dao;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import java.util.List;

public class FundDAO2 {
    public static void main(String[] args) {
    	System.setProperty("webdriver.firefox.driver", "/Users/dyongsong/Desktop/Sele/fire/geckodriver");

        WebDriver driver = new FirefoxDriver();
        FirefoxOptions option = new FirefoxOptions();
		option.addArguments("-headless");

        try {
            String url = "https://www.wadiz.kr/web/wreward/main";
            driver.get(url);

            List<WebElement> posterElements = null;
            List<WebElement> linkElements=null;
            int count = 1;


            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            int totalCount = 0;
            int jPlus=0;
            while (true) {
                // 이전에 출력한 포스터들의 총 개수를 저장하여 비교에 사용
                int previousTotalCount = totalCount;

                for (int j = jPlus; j < jPlus+3; j++) { // 4번 스크롤을 나누어서 진행
                    js.executeScript("window.scrollTo(0, document.body.scrollHeight / 3 * " + j + ");");
                    Thread.sleep(1000); // 스크롤을 내리고 기다리는 시간 (1초)
                }
                // PreorderMainCard_item__2FrO0
                // PreorderMainCard_item__2FrO0
                // 포스터 가져오기
                posterElements = driver.findElements(By.className("CardThumbnail_thumbnail__3bDBJ"));
                linkElements = driver.findElements(By.className("PreorderMainCard_item__2FrO0"));

                // 새로운 포스터들 출력
                for (int i = totalCount; i < posterElements.size(); i++) {
                    String posterStyle = posterElements.get(i).getAttribute("style");
                    String linkhref = linkElements.get(i).getAttribute("href");
                    if (posterStyle.contains("background-image: url")) {
                        String poster = extractImageUrl(posterStyle);
                        System.out.println("poster: " + poster);
                        System.out.println("link: " + linkhref);
                        count++;
                    }
                }

                // 이전에 출력한 포스터들의 총 개수를 현재 포스터들의 개수로 업데이트
                totalCount = posterElements.size();

                // 더 이상 새로운 포스터가 로드되지 않으면 종료
                if (previousTotalCount == totalCount) {
                    break;
                }
                jPlus+=3;
                Thread.sleep(3000);
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