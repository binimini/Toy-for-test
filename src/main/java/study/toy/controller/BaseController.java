package study.toy.controller;


import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.toy.dto.UsingChromeDriver;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class BaseController{
    @Autowired
    private  WebDriver driver;
    private static final String url = "https://lolesports.com/schedule?leagues=lck";

    @UsingChromeDriver
    @GetMapping("/schedule")
    public void schedule() throws Exception{
        driver.get(url);
        List<WebElement> dates = driver.findElements(By.className("EventDate"));
        List<WebElement> matches = driver.findElements(By.className("EventMatch"));
        System.out.println("WHY????");
        System.out.println(dates.size());
        System.out.println(matches.size());
        for ( int i = 0; i<dates.size(); i++) {
            String tmp = dates.get(i).getText();
            System.out.println(tmp);
            tmp = matches.get(2*i).getText();
            System.out.println(tmp);
            tmp = matches.get(2*i+1).getText();
            System.out.println(tmp);

        }
    }
    @UsingChromeDriver
    @GetMapping("/standings")
    public void standings() throws Exception{
    }

    @UsingChromeDriver
    @GetMapping("/byTeam")
    public void ByTeam() throws Exception{

    }

    @UsingChromeDriver
    @GetMapping("/byDate")
    public void ByDate() throws Exception{

    }
}