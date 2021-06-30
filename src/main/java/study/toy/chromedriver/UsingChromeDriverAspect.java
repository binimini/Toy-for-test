package study.toy.chromedriver;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
//모든 함수 실행 직전에 set up - 실행 후 quit
public class UsingChromeDriverAspect {
    @Autowired
    private WebDriver driver;

    @Around("@annotation(UsingChromeDriver)")
    public Object setUpAndQuit(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("before");
        Object proceed = joinPoint.proceed(); // 타겟 메소드 실행
        System.out.println("after");
        return proceed;
    }
}
