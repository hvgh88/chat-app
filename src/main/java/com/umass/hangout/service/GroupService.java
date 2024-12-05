package com.umass.hangout.service;

import com.umass.hangout.entity.Group;
import com.umass.hangout.entity.Message;
import com.umass.hangout.entity.User;
import com.umass.hangout.repository.elasticsearch.GroupSearchRepository;
import com.umass.hangout.repository.elasticsearch.MessageSearchRepository;
import com.umass.hangout.repository.jpa.GroupRepository;
import com.umass.hangout.repository.jpa.MessageRepository;
import com.umass.hangout.repository.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GroupService {
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

    public Group createGroup(Group group) {
        Group savedGroup = groupRepository.save(group);
        groupSearchRepository.save(savedGroup);
        return savedGroup;

    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public List<Message> getGroupMessages(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        return messageRepository.findByGroupOrderByTimestampAsc(group);
    }

    @Transactional
    public Message sendMessage(Long groupId, Long userId, String content) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Message message = new Message();
        message.setContent(content);
        message.setSender(user);
        message.setGroup(group);
        return messageRepository.save(message);
    }


    @Transactional
    public void joinGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getGroupIds().add(groupId)) {
            userRepository.save(user);
        } else {
            throw new RuntimeException("User is already part of this group");
        }
    }

    public List<Group> getUserGroups(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return groupRepository.findAllById(user.getGroupIds());
    }
}