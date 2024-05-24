package com.amcef.svancarek.testovaciezadanie.postservice.model;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @Max(value = 100, message = "userId must be lower than or equal to 100")
    @Min(value = 1, message = "userId must be greater than or equal to 1")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Max(value = 10, message = "userId must be lower than or equal to 10")
    @Min(value = 1, message = "userId must be greater than or equal to 1")
    @NotNull
    private Integer userId;

    @NotBlank
    @Size(max = 255, message = "title cant be longer than 255 characters")
    private String title;

    @NotBlank
    @Size(max = 5000, message = "body cant be longer than 5000 characters")
    private String body;
}