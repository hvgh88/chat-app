package com.umass.hangout.controller;

import com.umass.hangout.entity.Group;
import com.umass.hangout.entity.Message;
import com.umass.hangout.entity.MessageDTO;
import com.umass.hangout.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/messages/{groupId}")
    public List<Message> getGroupMessages(@PathVariable Long groupId) {
        return groupService.getGroupMessages(groupId);
    }

    @PostMapping("/create")
    public Group createGroup(@RequestBody Group group) {
        return groupService.createGroup(group);
    }

    @PostMapping("/join")
    public void joinGroup(@RequestParam Long groupId, @RequestParam Long userId) {
        groupService.joinGroup(groupId, userId);
    }

    @GetMapping("/all")
    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping("/user/{userId}")
    public List<Group> getUserGroups(@PathVariable Long userId) {
        return groupService.getUserGroups(userId);
    }

    @MessageMapping("/chat/{groupId}")
    public void handleMessage(@DestinationVariable Long groupId, Message message) {
        Message savedMessage = groupService.sendMessage(groupId, message.getSender().getId(), message.getContent());
        MessageDTO messageDTO = new MessageDTO(
                savedMessage.getId(),
                savedMessage.getContent(),
                savedMessage.getSender().getId(),
                savedMessage.getGroup().getId()
        );
        messagingTemplate.convertAndSend("/topic/messages/" + groupId, messageDTO);
    }
}