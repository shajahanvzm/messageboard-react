package com.sha.mb.service.impl;

import com.sha.mb.entity.Message;
import com.sha.mb.payload.MessageDto;
import com.sha.mb.payload.MessageResponse;
import com.sha.mb.repository.MessageRepository;
import com.sha.mb.service.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.sha.mb.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ModelMapper mapper;

    public MessageServiceImpl(MessageRepository messageRepository, ModelMapper mapper) {
        this.messageRepository = messageRepository;
        this.mapper = mapper;
    }

    @Override
    public MessageDto save(MessageDto messageDto) {
        Message message = messageRepository.save(mapper.map(messageDto, Message.class));
        return mapper.map(message, MessageDto.class);
    }

    @Override
    public MessageDto update(Long id, MessageDto messageDto) {
        Message message = messageRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Message", "id", id));
        message.setEmail(messageDto.getEmail());
        message.setMessage(messageDto.getMessage());
        Message updatedMessage = messageRepository.save(message);
        return mapper.map(updatedMessage, MessageDto.class);
    }

    @Override
    public List<MessageDto> getAllMessage() {
        return messageRepository.findAll().stream().map(message -> mapper.map(message, MessageDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public MessageDto getMessageById(long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Message", "id", id));
        return mapper.map(message,MessageDto.class);
    }
    @Override
    public MessageResponse getAllMessage(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<Message> messages = messageRepository.findAll(pageable);

        List<MessageDto> content = messages.stream().map(message -> mapper.map(message, MessageDto.class))
                .collect(Collectors.toList());

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setContent(content);
        messageResponse.setPageNo(messages.getNumber());
        messageResponse.setPageSize(messages.getSize());
        messageResponse.setTotalElements(messages.getTotalElements());
        messageResponse.setTotalPages(messages.getTotalPages());
        messageResponse.setLast(messages.isLast());

        return messageResponse;
    }

    @Override
    public void deleteAll() {
        messageRepository.deleteAll();
    }

    @Override
    public void deleteById(long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Message", "id", id));
        messageRepository.deleteById(id);

    }
}
