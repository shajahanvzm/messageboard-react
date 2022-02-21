package com.sha.mb.service;

import com.sha.mb.payload.MessageDto;
import com.sha.mb.payload.MessageResponse;

import java.util.List;

public interface MessageService {

    public MessageDto save(MessageDto messageDto);
    public MessageDto update(Long id, MessageDto messageDto);
    public MessageResponse getAllMessage(int pageNo, int pageSize, String sortBy, String sortDir);
    public List<MessageDto> getAllMessage();
    public MessageDto getMessageById(long id);
    public void  deleteAll();
    public void deleteById(long id);

}
