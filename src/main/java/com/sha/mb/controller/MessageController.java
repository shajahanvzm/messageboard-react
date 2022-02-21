package com.sha.mb.controller;

import com.sha.mb.payload.MessageDto;
import com.sha.mb.payload.MessageResponse;
import com.sha.mb.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0/messages")
@CrossOrigin(value = "http://localhost:3000")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageDto> save(@Valid  @RequestBody MessageDto messageDto){
        return new ResponseEntity<>(messageService.save(messageDto), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<MessageDto> update(@PathVariable long id, @Valid @RequestBody MessageDto messageDto){
        return new ResponseEntity<>(messageService.update(id,messageDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDto> getById(@PathVariable long id){
        return new ResponseEntity<>(messageService.getMessageById(id), HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity<List<MessageDto>> getAll(){
//        return new ResponseEntity<>(messageService.getAllMessage(), HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<MessageResponse> getAll(
         @RequestParam(value = "pageNo", defaultValue = "0", required = false)  int pageNo,
         @RequestParam(value = "pageSize", defaultValue = "10", required = false)  int pageSize,
         @RequestParam(value = "sortBy", defaultValue = "id", required = false)  String sortBy,
         @RequestParam(value = "sortDir", defaultValue = "dsc", required = false)  String sortDir){
        return new ResponseEntity<>(messageService.getAllMessage(pageNo,pageSize,sortBy,sortDir), HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id){
        messageService.deleteById(id);
        return new ResponseEntity<>("Message entity deleted successfully", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll(){
        messageService.deleteAll();
        return new ResponseEntity<>("Messages Table is empty!", HttpStatus.OK);
    }

}
