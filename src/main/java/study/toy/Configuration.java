package study.toy;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Queue;

@org.springframework.context.annotation.Configuration
public class Configuration {
    private static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    private static final String WEB_DRIVER_PATH = "C:/Users/USER/Downloads/chromedriver_win32/chromedriver.exe";
    @Bean
    public WebDriver webDriver() {
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        return new ChromeDriver(options);
    }
    @Value("${token}")
    private String token;

    @Bean
    public <T extends Event> GatewayDiscordClient gatewayDiscordClient(List<EventListener<T>> eventListeners) {
        GatewayDiscordClient client = DiscordClientBuilder.create(token)
                .build()
                .login()
                .block();

        for(int i = 0; i<eventListeners.size(); i++) {
            System.out.println(eventListeners.size()+" "+i);
            EventListener<T> listener = eventListeners.get(i);
            client.on(listener.getEventType())
                    .flatMap(listener::execute)
                    .subscribe();
        }

        return client;
    }
}
