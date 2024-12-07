package com.umass.hangout.controller;

import com.umass.hangout.entity.Group;
import com.umass.hangout.entity.Message;
import com.umass.hangout.entity.MessageDTO;
import com.umass.hangout.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private static final Logger log = LoggerFactory.getLogger(GroupController.class);

    @PostMapping("/create")
    public Group createGroup(@RequestBody Group group, @RequestParam Long userId) {
        return groupService.createGroup(group, userId);
    }

    @PostMapping("/join")
    public ResponseEntity<Void> joinGroup(@RequestParam Long groupId, @RequestParam Long userId) {
        groupService.joinGroup(groupId, userId);
        return ResponseEntity.ok().build();
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
    public void handleMessage(@DestinationVariable Long groupId, MessageDTO messageDTO) {
//        log.info("Received message DTO: {}", messageDTO);

        if (messageDTO == null || messageDTO.getContent() == null || messageDTO.getSenderId() == null) {
//            log.error("Invalid message DTO received");
            return;
        }

        try {
            MessageDTO savedMessageDTO = groupService.sendMessage(groupId, messageDTO);
//            log.info("OUT OF SERVICE METHOD, BACK TO CONTROLLER");
//            log.info("Saved message DTO, preparing to broadcast: {}", savedMessageDTO);
            messagingTemplate.convertAndSend("/topic/messages/" + groupId, savedMessageDTO);
//            log.info("Message Broadcasted successfully");
        } catch (Exception e) {
            log.error("Error processing message", e);
        }
    }

    @GetMapping("/{groupId}/messages")
    public List<Message> getPreviousMessages(@PathVariable Long groupId) {
        return groupService.getGroupMessages(groupId);
    }
}