package study.toy.configuration;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import study.toy.listener.EventListener;

import java.util.List;

@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class Configuration {
    @Value("${token}")
    private String token;
    @Value("${web_driver_path}")
    private String WEB_DRIVER_PATH;
    private static final String WEB_DRIVER_ID = "webdriver.chrome.driver";

    @Bean
    public WebDriver webDriver() {
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        return new ChromeDriver(options);
    }


    @Bean
    public <T extends Event> GatewayDiscordClient gatewayDiscordClient(List<EventListener<T>> eventListeners) {
        GatewayDiscordClient client = DiscordClientBuilder.create(token)
                .build()
                .login()
                .block();

        for(int i = 0; i<eventListeners.size(); i++) {
            EventListener<T> listener = eventListeners.get(i);
            client.on(listener.getEventType())
                    .flatMap(listener::execute)
                    .onErrorResume(listener::handleError)
                    .subscribe();
        }

        return client;
    }
}
