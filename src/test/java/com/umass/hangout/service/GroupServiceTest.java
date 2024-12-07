package com.umass.hangout.service;

import com.umass.hangout.entity.Group;
import com.umass.hangout.entity.User;
import com.umass.hangout.exception.GroupNotFoundException;
import com.umass.hangout.exception.UserAlreadyInGroupException;
import com.umass.hangout.exception.UserNotFoundException;
import com.umass.hangout.repository.jpa.GroupRepository;
import com.umass.hangout.repository.jpa.UserRepository;
import com.umass.hangout.service.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {
    @Mock
    private GroupRepository groupRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GroupService groupService;

    private User testUser;
    private Group testGroup;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("Test User");

        testGroup = new Group();
        testGroup.setId(1L);
        testGroup.setName("Test Group");
    }

    @Test
    void joinGroup_Success() {
        // Arrange
        when(groupRepository.findById(1L)).thenReturn(Optional.of(testGroup));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // Act
        groupService.joinGroup(1L, 1L);

        // Assert
        verify(userRepository).save(testUser);
    }

    @Test
    void joinGroup_GroupNotFound() {
        // Arrange
        when(groupRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(GroupNotFoundException.class, () ->
                groupService.joinGroup(1L, 1L));
    }

    @Test
    void joinGroup_UserNotFound() {
        // Arrange
        when(groupRepository.findById(1L)).thenReturn(Optional.of(testGroup));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () ->
                groupService.joinGroup(1L, 1L));
    }

    @Test
    void joinGroup_UserAlreadyInGroup() {
        // Arrange
        testUser.getGroupIds().add(1L);
        when(groupRepository.findById(1L)).thenReturn(Optional.of(testGroup));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // Act & Assert
        assertThrows(UserAlreadyInGroupException.class, () ->
                groupService.joinGroup(1L, 1L));
    }
}
