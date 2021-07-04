package study.toy.service;


import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import study.toy.chromedriver.UsingChromeDriver;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class CrawlingService {
    @Autowired
    private  WebDriver driver;
    @Autowired
    GatewayDiscordClient discordClient;
    private static final String url = "https://lolesports.com/schedule?leagues=lck";

    @UsingChromeDriver
    public List<String> schedule() throws Exception{
        log.info("Crawling started at "+url);
        driver.get(url);
        List<WebElement> dates = driver.findElements(By.className("EventDate"));
        List<WebElement> matches = driver.findElements(By.className("EventMatch"));
        System.out.println(dates.size());
        System.out.println(matches.size());

        String message = "";
        List<String> result = new ArrayList<>();
        for ( int i = 0; i<dates.size(); i++) {
            message = "";

            message += dates.get(i).getText();
            message += matches.get(2*i).getText();
            message += matches.get(2*i+1).getText();
            message +="\n";

            result.add(message);
        }
        System.out.println(result);
        log.info("Crawling ended");
        return result;
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