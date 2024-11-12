package com.umass.hangout.service;

import com.umass.hangout.entity.Chatroom;
import com.umass.hangout.entity.Message;
import com.umass.hangout.repository.ChatroomRepository;
import com.umass.hangout.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatroomRepository chatroomRepository;

    public Message postMessage(Long chatroomId, String content, String sender) {
        Chatroom chatroom = chatroomRepository.findById(chatroomId)
                .orElseThrow(() -> new RuntimeException("Chatroom not found"));

        Message message = new Message();
        message.setContent(content);
        message.setSender(sender);
        message.setChatroom(chatroom);
        message.setTimestamp(LocalDateTime.now());

        return messageRepository.save(message);
    }

    public List<Message> getMessages(Long chatroomId) {
        return messageRepository.findByChatroomId(chatroomId);
    }
}
