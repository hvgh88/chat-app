//package com.umass.hangout.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.umass.hangout.entity.Group;
//import com.umass.hangout.entity.Message;
//import com.umass.hangout.exception.GroupNotFoundException;
//import com.umass.hangout.exception.UserNotFoundException;
//import com.umass.hangout.service.GroupService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.HashSet;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//public class GroupControllerTest {
//
//    @Mock
//    private GroupService groupService;
//
//    @InjectMocks
//    private GroupController groupController;
//
//    private MockMvc mockMvc;
//
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    private Group group;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(groupController).build();
//
//        group = new Group();
//        group.setId(1L);
//        group.setName("Test Group");
//        group.setLocation("Location");
//        group.setDateTime(LocalDateTime.now());
//    }
//
//    @Test
//    public void testCreateGroup() throws Exception {
//        when(groupService.createGroup(any(Group.class), anyLong())).thenReturn(group);
//
//        mockMvc.perform(post("/group/create?userId=1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(group)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.name").value("Test Group"));
//    }
//
//    @Test
//    public void testCreateGroup_UserNotFound() throws Exception {
//        when(groupService.createGroup(any(Group.class), anyLong())).thenThrow(new UserNotFoundException("User not found"));
//
//        mockMvc.perform(post("/group/create?userId=1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(group)))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string("User not found"));
//    }
//
//    @Test
//    public void testJoinGroup() throws Exception {
//        mockMvc.perform(post("/group/join?groupId=1&userId=1"))
//                .andExpect(status().isOk());
//
//        // You can add more assertions if you want to verify the behavior of the joinGroup method.
//    }
//
////    @Test
////    public void testJoinGroup_GroupNotFound() throws Exception {
////        when(groupService.joinGroup(anyLong(), anyLong())).thenThrow(new GroupNotFoundException("Group not found"));
////
////        mockMvc.perform(post("/group/join?groupId=1&userId=1"))
////                .andExpect(status().isNotFound())
////                .andExpect(content().string("Group not found"));
////    }
//
//    @Test
//    public void testGetAllGroups() throws Exception {
//        when(groupService.getAllGroups()).thenReturn(Collections.singletonList(group));
//
//        mockMvc.perform(get("/group/all"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].name").value("Test Group"));
//    }
//
//    @Test
//    public void testGetUserGroups() throws Exception {
//        when(groupService.getUserGroups(anyLong())).thenReturn(Collections.singletonList(group));
//
//        mockMvc.perform(get("/group/user/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].name").value("Test Group"));
//    }
//
//    @Test
//    public void testGetUserGroups_UserNotFound() throws Exception {
//        when(groupService.getUserGroups(anyLong())).thenThrow(new UserNotFoundException("User not found"));
//
//        mockMvc.perform(get("/group/user/1"))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string("User not found"));
//    }
//
//}