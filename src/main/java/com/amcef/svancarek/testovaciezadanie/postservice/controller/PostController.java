package com.amcef.svancarek.testovaciezadanie.postservice.controller;

import com.amcef.svancarek.testovaciezadanie.postservice.dto.CreatePostDTO;
import com.amcef.svancarek.testovaciezadanie.postservice.dto.UpdatePostDTO;
import com.amcef.svancarek.testovaciezadanie.postservice.model.Post;
import com.amcef.svancarek.testovaciezadanie.postservice.service.PostService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@Validated
public class PostController {

    @Autowired
    private PostService postService;

    @Operation(summary = "Add a new post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json",
                            examples = {
                                    @ExampleObject(name = "User ID is too low", value = "{ \"error\": \"userId must be greater than or equal to 1\" }"),
                                    @ExampleObject(name = "User ID Exceeds Maximum Value", value = "{ \"error\": \"userId must be lower than or equal to 10\" }"),
                                    @ExampleObject(name = "Blank title", value = "{ \"error\": \"title must not be blank\" }"),
                                    @ExampleObject(name = "Blank body", value = "{ \"error\": \"body must not be blank\" }"),
                                    @ExampleObject(name = "Body length", value = "{ \"error\": \"body cant be longer than 5000 characters\" }"),
                                    @ExampleObject(name = "Title length", value = "{ \"error\": \"title cant be longer than 250 characters\" }")
                            })),
            @ApiResponse(responseCode = "503", description = "API communication error",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"error\": \"API communication error: Communication with API failed.\" }"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"error\": \"An unexpected error occurred.\" }")))
    })
    @PostMapping
    public ResponseEntity<Post> addPost(@Valid @RequestBody CreatePostDTO createPostDTO) {
        Post createdPost = postService.addPost(createPostDTO);
        return ResponseEntity.ok(createdPost);
    }

    @Operation(summary = "Get a post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved post",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json",
                            examples = {
                                    @ExampleObject(name = "Invalid Id below 1", value = "{ \"error\": \"Id must be greater than or equal to 1\" }"),
                                    @ExampleObject(name = "Invalid Id over 100", value = "{ \"error\": \"Id must be less than or equal to 100\" }"),
                                    @ExampleObject(name = "Invalid Id text input", value = "{ \"error\": \"Text is not a valid number\" }")
                            })),
            @ApiResponse(responseCode = "503", description = "API communication error",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"error\": \"API communication error: Communication with API failed.\" }"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"error\": \"An unexpected error occurred.\" }")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(
            @Parameter(description = "ID of the post to retrieve")
            @PathVariable @Valid @Min(1) @Max(100) Integer id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @Operation(summary = "Get posts by User ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved posts",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json",
                            examples = {
                                    @ExampleObject(name = "Invalid userId below 1", value = "{ \"error\": \"must be less than or equal to 100\" }"),
                                    @ExampleObject(name = "Invalid userId over 100", value = "{ \"error\": \"must be greater than or equal to 1\" }"),
                                    @ExampleObject(name = "Invalid userId text input", value = "{ \"error\": \"Text is not a valid number\" }")
                            })),
            @ApiResponse(responseCode = "404", description = "Posts not found for user",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"error\": \"Posts not found for user\" }"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"error\": \"An unexpected error occurred.\" }")))
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getPostsByUserId(
            @Parameter(description = "User ID to retrieve posts for")
            @PathVariable @Valid @Min(1) @Max(100) Integer userId) {
        List<Post> posts = postService.getPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    //just for testing purposes
    @Operation(summary = "Get all posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all posts",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"error\": \"An unexpected error occurred.\" }")))
    })
    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }
    //just for testing purposes


    @Operation(summary = "Delete a post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Post successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Post not found or is already deleted",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"error\": \"Post not found or is already deleted\" }"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"error\": \"An unexpected error occurred.\" }")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(
            @Parameter(description = "ID of the post to delete")
            @PathVariable @Valid @Min(1) @Max(100) Integer id) {
        try {
            postService.deletePost(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "Post not found or is already deleted"));
        }
    }


    //just for testing purposes
    @Operation(summary = "Delete all posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "All posts successfully deleted"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"error\": \"An unexpected error occurred.\" }")))
    })
    @DeleteMapping("/deleteall")
    public ResponseEntity<Void> deleteAllPosts() {
        postService.deleteAllPosts();
        return ResponseEntity.noContent().build();
    }
    //just for testing purposes

    @Operation(summary = "Update a post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post successfully updated"),
            @ApiResponse(responseCode = "404", description = "Post not found",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"error\": \"Post not found\" }"))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json",
                            examples = {
                                    @ExampleObject(name = "Invalid Id below 1", value = "{ \"error\": \"must be less than or equal to 100\" }"),
                                    @ExampleObject(name = "Invalid Id over 100", value = "{ \"error\": \"must be greater than or equal to 1\" }"),
                                    @ExampleObject(name = "Invalid Id text input", value = "{ \"error\": \"Text is not a valid number\" }"),
                                    @ExampleObject(name = "Post not found", value = "{ \"error\": \"Post not found\" }"),
                                    @ExampleObject(name = "Blank title", value = "{ \"error\": \"title must not be blank\" }"),
                                    @ExampleObject(name = "Blank body", value = "{ \"error\": \"body must not be blank\" }"),
                                    @ExampleObject(name = "Body length", value = "{ \"error\": \"body cant be longer than 5000 characters\" }"),
                                    @ExampleObject(name = "Title length", value = "{ \"error\": \"title cant be longer than 250 characters\" }")
                            })),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"error\": \"An unexpected error occurred.\" }")))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(
            @PathVariable @Valid @Min(1) @Max(100) Integer id, @Valid @RequestBody UpdatePostDTO updatedPostDTO) {
        Post post = postService.updatePost(id, updatedPostDTO);
        return ResponseEntity.ok(post);
    }
}