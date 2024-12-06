package com.umass.hangout.service;

import com.umass.hangout.controller.GroupController;
import com.umass.hangout.entity.Group;
import com.umass.hangout.entity.Message;
import com.umass.hangout.entity.MessageDTO;
import com.umass.hangout.entity.User;
import com.umass.hangout.exception.GroupNotFoundException;
import com.umass.hangout.exception.UserNotFoundException;
import com.umass.hangout.exception.UserNotInGroupException;
import com.umass.hangout.repository.elasticsearch.GroupSearchRepository;
import com.umass.hangout.repository.elasticsearch.MessageSearchRepository;
import com.umass.hangout.repository.jpa.GroupRepository;
import com.umass.hangout.repository.jpa.MessageRepository;
import com.umass.hangout.repository.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GroupService {
    private static final Logger log = LoggerFactory.getLogger(GroupService.class);
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupSearchRepository groupSearchRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageSearchRepository messageSearchRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Group createGroup(Group group, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        Group savedGroup = groupRepository.save(group);
        groupSearchRepository.save(savedGroup);

        user.getGroupIds().add(savedGroup.getId());
        userRepository.save(user);

        return savedGroup;
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Transactional
    public MessageDTO sendMessage(Long groupId, MessageDTO messageDTO) {
        log.info("Entering sendMessage method. GroupId: {}, MessageDTO: {}", groupId, messageDTO);
        User sender = userRepository.findById(messageDTO.getSenderId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group not found"));

        Message message = new Message();
        message.setContent(messageDTO.getContent());
        message.setSender(sender);
        message.setGroup(group);
        log.info("Message Object created: {}", message);
        Message savedMessage = messageRepository.save(message);
        log.info("Message Object saved: {}", savedMessage);
        messageSearchRepository.save(savedMessage);
        log.info("Message Object saved in ES: {}", savedMessage);
        return new MessageDTO(
                savedMessage.getId(),
                savedMessage.getContent(),
                savedMessage.getSender().getId(),
                savedMessage.getGroup().getId(),
                savedMessage.getSender().getUsername()
        );
    }

    @Transactional
    public void joinGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group not found with ID: " + groupId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        if (user.getGroupIds().add(groupId)) {
            userRepository.save(user);
        } else {
            throw new RuntimeException("User is already part of this group");
        }
    }

    public List<Group> getUserGroups(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        return groupRepository.findAllById(user.getGroupIds());
    }

    public List<Message> getGroupMessages(Long groupId) {
        return messageRepository.findByGroupIdOrderByIdAsc(groupId);
    }
}