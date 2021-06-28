package study.toy.controller;


import lombok.AllArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import study.toy.dto.Hello;
import study.toy.dto.World;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@RestController
public class BaseController{
    private  WebDriver driver;

    private static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    private static final String WEB_DRIVER_PATH = "C:/Users/USER/Downloads/chromedriver_win32/chromedriver.exe";
    private static final String url = "https://lolesports.com/schedule?leagues=lck";
    Element element;

    public void setUp(){
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }
    public void quit(){
        driver.quit();
    }
    @PostConstruct
    public void intialize() throws Exception{
        setUp();
        driver.get(url);
        List<WebElement> dates = driver.findElements(By.className("EventDate"));
        List<WebElement> matches = driver.findElements(By.className("EventMatch"));
        for ( int i = 0; i<dates.size(); i++) {
            String tmp = dates.get(i).getText();
            System.out.println(tmp);
            tmp = matches.get(2*i).getText();
            System.out.println(tmp);
            tmp = matches.get(2*i+1).getText();
            System.out.println(tmp);

        }
        quit();
    }
}