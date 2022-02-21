package com.sha.mb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sha.mb.payload.MessageDto;
import com.sha.mb.payload.MessageResponse;
import com.sha.mb.service.MessageService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MessageController.class)
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService service;

    private static ObjectMapper mapper= new ObjectMapper();

    @Test
    void save() throws Exception {
        MessageDto messageDto = new MessageDto(1,"Test@ga.com","Test Message");
        when(service.save(ArgumentMatchers.any())).thenReturn(messageDto);
        String json = mapper.writeValueAsString(messageDto);
        mockMvc.perform(post("/api/v1.0/messages").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.equalTo((int) messageDto.getId())))
                .andExpect(jsonPath("$.email", Matchers.equalTo(messageDto.getEmail())));
    }

    //@Test
    void save_dto_validation_check() throws Exception {
        MessageDto messageDto = new MessageDto(1,"Test@gmail.com","Test Message");
        String result = "{\"message\":\"Message must be between 10 and 500 characters\",\"email\":\"Email must be a well-formed email address\"}";
        when(service.save(ArgumentMatchers.any())).thenReturn(messageDto);
        String json = mapper.writeValueAsString(messageDto);
        mockMvc.perform(post("/api/v1.0/messages").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                        .content(result).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", Matchers.equalTo("Email must be a well-formed email address")))
                .andExpect(jsonPath("$.message", Matchers.equalTo("Message must be between 10 and 500 characters")));
    }

    @Test
    void update() throws Exception {
        MessageDto messageDto = new MessageDto(1,"Test@ga.com","Test Message");
        when(service.update(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(messageDto);
        String json = mapper.writeValueAsString(messageDto);
        mockMvc.perform(put("/api/v1.0/messages/1").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo((int) messageDto.getId())))
                .andExpect(jsonPath("$.email", Matchers.equalTo(messageDto.getEmail())));
    }

    @Test
    void getById() throws Exception {
        MessageDto messageDto = new MessageDto(1,"Test@ga.com","Test Message");
        when(service.getMessageById(1l)).thenReturn(messageDto);
        String json = mapper.writeValueAsString(messageDto);
        mockMvc.perform(get("/api/v1.0/messages/1").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo( (int)messageDto.getId())))
                .andExpect(jsonPath("$.email", Matchers.equalTo(messageDto.getEmail())));

    }

   // @Test
    void getAll() throws Exception {
        MessageResponse messageResponse = new MessageResponse();
        ArrayList<MessageDto> messageDtos = new ArrayList<>();
            messageDtos.add(new MessageDto(1,"one@gmail.com","one Test Message"));
            messageDtos.add(new MessageDto(2,"two@gmail.com","two Test Message"));
            messageDtos.add(new MessageDto(3,"three@gmail.com","three Test Message"));
            messageDtos.add(new MessageDto(4,"four@gmail.com","four Test Message"));
            messageDtos.add(new MessageDto(5,"five@gmail.com","five Test Message"));
        messageResponse.setContent(messageDtos);
        messageResponse.setPageNo(0);
        messageResponse.setPageSize(10);
        messageResponse.setTotalElements(5);
        messageResponse.setTotalPages(1);
        messageResponse.setLast(true);
        when(service.getAllMessage(0,10,"id","dsc")).thenReturn(messageResponse);
        String json = mapper.writeValueAsString(messageResponse);
        System.out.println("json +++ " + json);
        mockMvc.perform(get("/api/v1.0/messages/1").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.pageno", Matchers.equalTo(messageResponse.getPageNo())));
                /*.andExpect(jsonPath("$.email", Matchers.equalTo(messageResponse.getContent().get(0).getEmail())));*/
    }

    @Test
    void deleteById() throws Exception {

        //Message entity deleted successfully
    }

    @Test
    void deleteAll() {
    }
}