package dev.hao.learn.springstartupoptimization;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    public MessageController(MessageRepository messageRepository) {
        // do nothing
    }
}
