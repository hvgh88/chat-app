package com.umass.hangout.controller;

import com.umass.hangout.entity.Message;
import com.umass.hangout.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat/{chatroomId}/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<Message> postMessage(
            @PathVariable Long chatroomId,
            @RequestBody Map<String, String> request) {

        String content = request.get("content");
        String sender = request.get("sender");

        Message message = messageService.postMessage(chatroomId, content, sender);
        return ResponseEntity.ok(message);
    }

    @GetMapping
    public List<Message> getMessages(@PathVariable Long chatroomId) {
        return messageService.getMessages(chatroomId);
    }
}
