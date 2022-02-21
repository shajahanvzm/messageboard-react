package com.sha.mb.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    private long id;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 10, max = 500, message = "Message must be between 10 and 500 characters ")
    private String message;

    public MessageDto(String email, String message) {
        this.email = email;
        this.message = message;
    }
}
