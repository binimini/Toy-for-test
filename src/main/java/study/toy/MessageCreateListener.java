package study.toy;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MessageCreateListener implements EventListener<MessageCreateEvent> {
    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        return Mono.just(event.getMessage())
                .filter(message -> message.getContent().equalsIgnoreCase("!test"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("response\n"))
                .then();
        //filter 없을 시 무한루프 원인?
    }
}
