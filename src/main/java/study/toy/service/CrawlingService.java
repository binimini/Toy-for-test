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
import java.util.Calendar;
import java.util.List;

@Slf4j
@RestController
public class CrawlingService {
    private final WebDriver driver;

    private static final String url = "https://lolesports.com/schedule?leagues=lck";

    public CrawlingService(WebDriver driver, GatewayDiscordClient discordClient){
        // 생성자 주입 순환 참조 컴파일 시기에 방지, final(불변성 확보) 가능, DI 프레임워크 없어도 작동
        this.driver = driver;
    }

    @UsingChromeDriver
    public List<String> schedule(int startMonth, int startDay) throws Exception{
        Long startTime = System.currentTimeMillis();
        log.info("Crawling started at "+url);
        driver.get(url);
        List<WebElement> datesAndMatches = driver.findElements(By.cssSelector(".EventDate, .EventMatch"));
        Long midTime1 = System.currentTimeMillis();
        int month, day, messageCnt = 0;
        if (startMonth==0&&startDay==0){
            Calendar calendar = Calendar.getInstance();
            month = calendar.get(Calendar.MONTH) + 1;
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }
        else {
            month = startMonth;
            day = startDay;
        }
        log.info("Setting date to "+month+"월 "+day+"일\n");
        List<String> result = new ArrayList<>();
        Long midTime2 = System.currentTimeMillis();
        for ( int i = 0; i<datesAndMatches.size(); i++) {
            if (messageCnt==5) break;

            String message = "";
            String queryStr = datesAndMatches.get(i).getText();
            if (!(queryStr.contains("요일"))) continue;
            int firstIdx = queryStr.indexOf("–"), firstIdxEnds = queryStr.indexOf("월", firstIdx);
            int secondIdx = queryStr.indexOf(" ",firstIdx), secondIdxEnds = queryStr.indexOf("일", secondIdx);
            int queryMonth =Integer.parseInt(queryStr.substring(firstIdx+1, firstIdxEnds));
            int queryDay = Integer.parseInt(queryStr.substring(secondIdx+1,secondIdxEnds));
            if (!(queryMonth>month||(queryMonth==month&&queryDay>=day))) continue;

            message += datesAndMatches.get(i).getText();
            message += '\n';
            message += datesAndMatches.get(i+1).getText().replaceAll("LCK\n최대 경기 수 3", "").replaceAll("\n", " ");
            if (!datesAndMatches.get(i+2).getText().contains("요일")) {
                message += "\n";
                message += datesAndMatches.get(i+2).getText().replaceAll("LCK\n최대 경기 수 3", "").replaceAll("\n"," ");
                i+=1;
            }
            i+=1;
            message += "\n\n";
            result.add(message);
            messageCnt++;
        }
        Long endTime = System.currentTimeMillis();
        log.info((midTime1- startTime)+" "+(midTime2- startTime)+" "+(endTime- startTime));
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