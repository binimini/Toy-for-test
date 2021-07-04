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
        log.info("Message Content : "+event.getMessage().getContent()+" Created!");
        if (event.getMessage().getContent().equals("!schedule")) {
            try {
                scheduleFlux = Flux.fromIterable(service.schedule());
            } catch (Exception e) {
                return Mono.empty();
            }
        }
        return scheduleFlux.flatMap(str -> event.getMessage().getChannel().flatMap(channel->channel.createMessage(str))).then().log();
    }
}
