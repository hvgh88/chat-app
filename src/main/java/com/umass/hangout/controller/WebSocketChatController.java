package com.umass.hangout.controller;

import com.umass.hangout.entity.Message;
import com.umass.hangout.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketChatController {

    @Autowired
    private MessageService messageService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Message sendMessage(Message message) {
        // Persist the message in the database
        Message savedMessage = messageService.postMessage(
                message.getChatroom().getId(),
                message.getContent(),
                message.getSender()
        );

        return savedMessage;
    }
}
