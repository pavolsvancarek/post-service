package com.amcef.svancarek.testovaciezadanie.postservice.service;

import com.amcef.svancarek.testovaciezadanie.postservice.client.JsonPlaceholderClient;
import com.amcef.svancarek.testovaciezadanie.postservice.dto.CreatePostDTO;
import com.amcef.svancarek.testovaciezadanie.postservice.dto.UpdatePostDTO;
import com.amcef.svancarek.testovaciezadanie.postservice.exception.ApiCommunicationException;
import com.amcef.svancarek.testovaciezadanie.postservice.model.Post;
import com.amcef.svancarek.testovaciezadanie.postservice.repository.PostRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private JsonPlaceholderClient jsonPlaceholderClient;

    @InjectMocks
    private PostService postService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddPost_Success() {
        CreatePostDTO createPostDTO = new CreatePostDTO();
        createPostDTO.setUserId(1);
        createPostDTO.setTitle("Test Title");
        createPostDTO.setBody("Test Body");

        when(jsonPlaceholderClient.getUserById(1)).thenReturn(new Object());

        Post post = new Post();
        post.setUserId(1);
        post.setTitle("Test Title");
        post.setBody("Test Body");

        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post createdPost = postService.addPost(createPostDTO);

        assertNotNull(createdPost);
        assertEquals("Test Title", createdPost.getTitle());
        assertEquals("Test Body", createdPost.getBody());
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddPost_InvalidUserId() {
        CreatePostDTO createPostDTO = new CreatePostDTO();
        createPostDTO.setUserId(999);
        createPostDTO.setTitle("Test Title");
        createPostDTO.setBody("Test Body");

        postService.addPost(createPostDTO);
    }

    @Test
    public void testGetPostsByUserId_Success() {
        Post post1 = new Post();
        post1.setId(1);
        post1.setUserId(1);
        post1.setTitle("Test Title 1");
        post1.setBody("Test Body 1");

        Post post2 = new Post();
        post2.setId(2);
        post2.setUserId(1);
        post2.setTitle("Test Title 2");
        post2.setBody("Test Body 2");

        when(postRepository.findByUserId(1)).thenReturn(Arrays.asList(post1, post2));

        List<Post> posts = postService.getPostsByUserId(1);

        assertNotNull(posts);
        assertEquals(2, posts.size());
        verify(postRepository, times(1)).findByUserId(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPostsByUserId_NotFound() {
        when(postRepository.findByUserId(1)).thenReturn(Arrays.asList());

        postService.getPostsByUserId(1);
    }

    @Test
    public void testGetPostById_Success() {
        Post post = new Post();
        post.setId(1);
        post.setUserId(1);
        post.setTitle("Test Title");
        post.setBody("Test Body");

        when(postRepository.findById(1)).thenReturn(Optional.of(post));

        Post foundPost = postService.getPostById(1);

        assertNotNull(foundPost);
        assertEquals("Test Title", foundPost.getTitle());
        verify(postRepository, times(1)).findById(1);
    }

    @Test(expected = ApiCommunicationException.class)
    public void testGetPostById_NotFound() {
        when(postRepository.findById(1)).thenReturn(Optional.empty());
        when(jsonPlaceholderClient.getPostById(1)).thenReturn(null);

        postService.getPostById(1);
    }

    @Test
    public void testDeletePost_Success() {
        when(postRepository.existsById(1)).thenReturn(true);

        postService.deletePost(1);

        verify(postRepository, times(1)).deleteById(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeletePost_NotFound() {
        when(postRepository.existsById(1)).thenReturn(false);

        postService.deletePost(1);
    }

    @Test
    public void testUpdatePost_Success() {
        Post post = new Post();
        post.setId(1);
        post.setUserId(1);
        post.setTitle("Original Title");
        post.setBody("Original Body");

        when(postRepository.findById(1)).thenReturn(Optional.of(post));

        UpdatePostDTO updatePostDTO = new UpdatePostDTO();
        updatePostDTO.setTitle("Updated Title");
        updatePostDTO.setBody("Updated Body");

        Post updatedPost = new Post();
        updatedPost.setId(1);
        updatedPost.setUserId(1);
        updatedPost.setTitle("Updated Title");
        updatedPost.setBody("Updated Body");

        when(postRepository.save(any(Post.class))).thenReturn(updatedPost);

        Post result = postService.updatePost(1, updatePostDTO);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        verify(postRepository, times(1)).findById(1);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdatePost_NotFound() {
        when(postRepository.findById(1)).thenReturn(Optional.empty());

        UpdatePostDTO updatePostDTO = new UpdatePostDTO();
        updatePostDTO.setTitle("Updated Title");
        updatePostDTO.setBody("Updated Body");

        postService.updatePost(1, updatePostDTO);
    }
}