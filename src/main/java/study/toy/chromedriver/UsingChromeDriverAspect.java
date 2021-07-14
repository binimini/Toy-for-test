package study.toy.chromedriver;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
//모든 함수 실행 직전에 set up - 실행 후 quit
public class UsingChromeDriverAspect {
    private final WebDriver driver;

    public UsingChromeDriverAspect(WebDriver driver){
        this.driver = driver;
    }
}
