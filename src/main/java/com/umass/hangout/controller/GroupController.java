package com.umass.hangout.controller;

import com.umass.hangout.entity.Group;
import com.umass.hangout.entity.Message;
import com.umass.hangout.service.GroupService;
import com.umass.hangout.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @Autowired
    private SearchService searchService;

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

    @GetMapping("/search")
    public List<Group> searchGroups(@RequestParam String keyword) {
        return searchService.searchGroups(keyword);
    }

    @GetMapping("/{groupId}/messages/search")
    public List<Message> searchMessagesInGroup(@PathVariable Long groupId, @RequestParam String keyword) {
        return searchService.searchMessagesInGroup(groupId, keyword);
    }

    @MessageMapping("/chat/{groupId}")
    @SendTo("/topic/messages/{groupId}")
    public Message sendMessage(@DestinationVariable Long groupId, Message message) {
        return groupService.sendMessage(groupId, message.getSender().getId(), message.getContent());
    }
}