//package com.umass.hangout.service;
//
//import com.umass.hangout.entity.Group;
//import com.umass.hangout.entity.Message;
//import com.umass.hangout.entity.User;
//import com.umass.hangout.exception.GroupNotFoundException;
//import com.umass.hangout.exception.UserNotFoundException;
//import com.umass.hangout.repository.jpa.GroupRepository;
//import com.umass.hangout.repository.jpa.MessageRepository;
//import com.umass.hangout.repository.jpa.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//public class GroupServiceTest {
//
//    @Mock
//    private GroupRepository groupRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private MessageRepository messageRepository;
//
//    @InjectMocks
//    private GroupService groupService;
//
//    private Group group;
//    private User user;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        group = new Group();
//        group.setId(1L);
//        group.setName("Test Group");
//        group.setLocation("Location");
//        group.setDateTime(LocalDateTime.now());
//
//        user = new User();
//        user.setId(1L);
//        user.setUsername("user1");
//        user.getGroupIds().add(group.getId());
//    }
//
//    @Test
//    public void testCreateGroup() {
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        when(groupRepository.save(any(Group.class))).thenReturn(group);
//
//        Group createdGroup = groupService.createGroup(group, 1L);
//
//        assertNotNull(createdGroup);
//        assertEquals("Test Group", createdGroup.getName());
//    }
//
//    @Test
//    public void testCreateGroup_UserNotFound() {
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(UserNotFoundException.class, () -> groupService.createGroup(group, 1L));
//    }
//
//
//
//
//}