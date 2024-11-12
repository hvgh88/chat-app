package com.umass.hangout.service;


import com.umass.hangout.entity.Chatroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.umass.hangout.repository.ChatroomRepository;

import java.util.Optional;

@Service
public class ChatroomService {
    @Autowired
    private ChatroomRepository chatroomRepository;

    public Chatroom createChatroom(String name) {
        Chatroom chatroom = new Chatroom();
        chatroom.setName(name);
        return chatroomRepository.save(chatroom);
    }

    public Optional<Chatroom> getChatroom(Long id) {
        return chatroomRepository.findById(id);
    }
}
