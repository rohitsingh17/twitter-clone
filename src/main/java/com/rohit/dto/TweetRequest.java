package com.rohit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetRequest {
    @NotBlank(message = "Tweet content cannot be empty")
    @Size(max = 160, message = "Tweet content cannot exceed 160 characters")
    private String content;
}
