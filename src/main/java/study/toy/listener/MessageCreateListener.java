package study.toy.listener;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import study.toy.listener.EventListener;
import study.toy.service.CrawlingService;

import java.util.List;

@Slf4j
@Service
public class MessageCreateListener implements EventListener<MessageCreateEvent> {
    @Autowired
    CrawlingService service;

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        Flux<String> scheduleFlux = null;

        if (!event.getMessage().getAuthor().get().isBot()) {                //봇 메세지 제외
            log.info("Message Content : " + event.getMessage().getContent() + " Created!");
            if (event.getMessage().getContent().contains("!schedule")) {      //!schedule
                try {
                    long startTime = System.currentTimeMillis();
                    String[] dates = event.getMessage().getContent().split(" ");
                    if (dates.length==1)                                 //date 입력 체크
                        scheduleFlux = Flux.fromIterable(service.schedule(0,0));
                    else
                        scheduleFlux = Flux.fromIterable(service.schedule(Integer.parseInt(dates[1].substring(0,2)), Integer.parseInt(dates[1].substring(2,4))));
                    long endTime = System.currentTimeMillis();
                    log.info("duration time : "+(endTime-startTime)+"milliseconds\n");
                    return scheduleFlux.flatMap(str -> event.getMessage().getChannel().flatMap(channel -> channel.createMessage(str))).then().log();
                }
                catch (Exception e) {                                     //Crawling exception
                    log.error("Crawling Service Exception is threw");
                    log.error(e.toString());
                    log.error(e.getMessage());
                }
            }

        }
        return Mono.empty();
    }
}
