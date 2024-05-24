package com.amcef.svancarek.testovaciezadanie.postservice.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePostDTO {

    @NotBlank
    @Size(max = 255, message = "title cant be longer than 255 characters")
    private String title;

    @NotBlank
    @Size(max = 5000, message = "body cant be longer than 5000 characters")
    private String body;
}
