package com.amcef.svancarek.testovaciezadanie.postservice.controller;

import com.amcef.svancarek.testovaciezadanie.postservice.dto.CreatePostDTO;
import com.amcef.svancarek.testovaciezadanie.postservice.dto.UpdatePostDTO;
import com.amcef.svancarek.testovaciezadanie.postservice.model.Post;
import com.amcef.svancarek.testovaciezadanie.postservice.service.PostService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddPost() {
        CreatePostDTO createPostDTO = new CreatePostDTO();
        createPostDTO.setUserId(1);
        createPostDTO.setTitle("Test Title");
        createPostDTO.setBody("Test Body");

        Post mockPost = new Post();
        mockPost.setId(1);
        mockPost.setUserId(1);
        mockPost.setTitle("Test Title");
        mockPost.setBody("Test Body");

        when(postService.addPost(any(CreatePostDTO.class))).thenReturn(mockPost);

        ResponseEntity<Post> response = postController.addPost(createPostDTO);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPost, response.getBody());
    }

    @Test
    public void testGetPostById() {
        Post mockPost = new Post();
        mockPost.setId(1);
        mockPost.setUserId(1);
        mockPost.setTitle("Test Title");
        mockPost.setBody("Test Body");

        when(postService.getPostById(1)).thenReturn(mockPost);

        ResponseEntity<Post> response = postController.getPostById(1);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPost, response.getBody());
    }

    @Test
    public void testGetPostsByUserId() {
        Post mockPost = new Post();
        mockPost.setId(1);
        mockPost.setUserId(1);
        mockPost.setTitle("Test Title");
        mockPost.setBody("Test Body");

        List<Post> mockPostList = Collections.singletonList(mockPost);

        when(postService.getPostsByUserId(1)).thenReturn(mockPostList);

        ResponseEntity<List<Post>> response = postController.getPostsByUserId(1);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPostList, response.getBody());
    }

    @Test
    public void testGetAllPosts() {
        Post mockPost = new Post();
        mockPost.setId(1);
        mockPost.setUserId(1);
        mockPost.setTitle("Test Title");
        mockPost.setBody("Test Body");

        List<Post> mockPostList = Collections.singletonList(mockPost);

        when(postService.getAllPosts()).thenReturn(mockPostList);

        ResponseEntity<List<Post>> response = postController.getAllPosts();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPostList, response.getBody());
    }

    @Test
    public void testDeletePost() {
        doNothing().when(postService).deletePost(1);

        ResponseEntity<?> response = postController.deletePost(1);
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testUpdatePost() {
        UpdatePostDTO updatePostDTO = new UpdatePostDTO();
        updatePostDTO.setTitle("Updated Title");
        updatePostDTO.setBody("Updated Body");

        Post mockPost = new Post();
        mockPost.setId(1);
        mockPost.setUserId(1);
        mockPost.setTitle("Updated Title");
        mockPost.setBody("Updated Body");

        when(postService.updatePost(eq(1), any(UpdatePostDTO.class))).thenReturn(mockPost);

        ResponseEntity<Post> response = postController.updatePost(1, updatePostDTO);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPost, response.getBody());
    }

    @Test
    public void testDeleteAllPosts() {
        doNothing().when(postService).deleteAllPosts();

        ResponseEntity<Void> response = postController.deleteAllPosts();
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
