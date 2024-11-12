package com.umass.hangout.controller;

import com.umass.hangout.entity.Chatroom;
import com.umass.hangout.service.ChatroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/chat")
public class ChatroomController {

    @Autowired
    private ChatroomService chatroomService;

    @PostMapping
    public Chatroom createChatroom(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        return chatroomService.createChatroom(name);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chatroom> getChatroom(@PathVariable Long id) {
        Optional<Chatroom> chatroom = chatroomService.getChatroom(id);
        return chatroom.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}