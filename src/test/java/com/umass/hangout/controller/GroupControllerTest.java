package com.umass.hangout.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umass.hangout.entity.Group;
import com.umass.hangout.entity.Message;
import com.umass.hangout.exception.GroupNotFoundException;
import com.umass.hangout.exception.InvalidInputException;
import com.umass.hangout.exception.UserAlreadyInGroupException;
import com.umass.hangout.exception.UserNotFoundException;
import com.umass.hangout.service.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class GroupControllerTest {
    @Mock
    private GroupService groupService;

    @InjectMocks
    private GroupController groupController;

    @Test
    void joinGroup_Success() {
        // Arrange
        doNothing().when(groupService).joinGroup(1L, 1L);

        // Act
        ResponseEntity<String> response = groupController.joinGroup("1", "1");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void joinGroup_InvalidGroupId() {
        // Act & Assert
        assertThrows(InvalidInputException.class, () ->
                groupController.joinGroup("invalid", "1"));
    }

    @Test
    void joinGroup_InvalidUserId() {
        // Act & Assert
        assertThrows(InvalidInputException.class, () ->
                groupController.joinGroup("1", "invalid"));
    }

    @Test
    void joinGroup_GroupNotFound() {
        // Arrange
        doThrow(new GroupNotFoundException("Group not found"))
                .when(groupService).joinGroup(1L, 1L);

        // Act
        ResponseEntity<String> response = groupController.joinGroup("1", "1");

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Group not found", response.getBody());
    }

    @Test
    void joinGroup_UserNotFound() {
        // Arrange
        doThrow(new UserNotFoundException("User not found"))
                .when(groupService).joinGroup(1L, 1L);

        // Act
        ResponseEntity<String> response = groupController.joinGroup("1", "1");

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }

    @Test
    void joinGroup_UserAlreadyInGroup() {
        // Arrange
        doThrow(new UserAlreadyInGroupException("User already in group"))
                .when(groupService).joinGroup(1L, 1L);

        // Act
        ResponseEntity<String> response = groupController.joinGroup("1", "1");

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User already in group", response.getBody());
    }
}