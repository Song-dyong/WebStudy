package com.sist.main;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

public class GptPlz {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.firefox.driver", "/Users/dyongsong/Desktop/Sele/fire/geckodriver");
        WebDriver driver = new FirefoxDriver();

        try {
            String url = "https://www.wadiz.kr/web/store/main";
            driver.get(url);

            JavascriptExecutor js = (JavascriptExecutor) driver;
            int k = 1;

            while (true) {
                // 현재 스크롤 위치를 가져옴
                long previousScrollY = (long) js.executeScript("return window.scrollY;");

                // 1/4만큼 스크롤을 내림
                js.executeScript("window.scrollTo(0, document.body.scrollHeight * " + (k + 1) + " / 4);");
                Thread.sleep(1000);

                for (int i = k; i <= k + 7; i++) {
                    WebElement posterElement = driver.findElement(By.xpath("/html/body/div[1]/main/div[2]/div[2]/div[4]/div[1]/div[" + i + "]/a/div[1]/div/div"));
                    if (posterElement == null) {
                        posterElement = driver.findElement(By.xpath("/html/body/div[1]/main/div[2]/div[2]/div[4]/div[1]/div[" + i + "]/a/div[1]/div/div[1]"));
                    }
                    WebElement linkElement = driver.findElement(By.xpath("/html/body/div[1]/main/div[2]/div[2]/div[4]/div[1]/div[" + i + "]/a"));

                    String poster = posterElement.getAttribute("style");
                    String link = linkElement.getAttribute("href");
                    System.out.println(k + "----------------------------");
                    System.out.println(poster);
                    System.out.println(link);
                    System.out.println("--------------------------------");
                    Thread.sleep(800);
                }

                k += 8;

                // 이전 스크롤 위치와 현재 스크롤 위치를 비교하여 스크롤이 더 이상 움직이지 않으면 종료
                long currentScrollY = (long) js.executeScript("return window.scrollY;");
                if (currentScrollY == previousScrollY) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
