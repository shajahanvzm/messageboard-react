package com.sha.mb;

import com.sha.mb.payload.MessageDto;
import com.sha.mb.service.MessageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbLoad implements CommandLineRunner {
    private final MessageService messageService;

    public DbLoad(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void run(String... args) throws Exception {
        messageService.save(new MessageDto("one@gmail.com","one message"));
        messageService.save(new MessageDto("two@gmail.com","two message"));
        messageService.save(new MessageDto("three@gmail.com","three message"));
        messageService.save(new MessageDto("four@gmail.com","four message"));
        messageService.save(new MessageDto("Five@gmail.com","Five message"));
    }
}
