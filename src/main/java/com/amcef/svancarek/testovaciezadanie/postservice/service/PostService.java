package com.amcef.svancarek.testovaciezadanie.postservice.service;

import com.amcef.svancarek.testovaciezadanie.postservice.client.JsonPlaceholderClient;
import com.amcef.svancarek.testovaciezadanie.postservice.dto.CreatePostDTO;
import com.amcef.svancarek.testovaciezadanie.postservice.exception.ApiCommunicationException;
import com.amcef.svancarek.testovaciezadanie.postservice.model.Post;
import com.amcef.svancarek.testovaciezadanie.postservice.dto.UpdatePostDTO;
import com.amcef.svancarek.testovaciezadanie.postservice.repository.PostRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private JsonPlaceholderClient jsonPlaceholderClient;

    public Post addPost(CreatePostDTO createPostDTO) {
        try {
            checkUserId(createPostDTO.getUserId());
            checkTitle(createPostDTO.getTitle());
            checkBody(createPostDTO.getBody());
        } catch (Exception e) {

        }
        try {
            Object user = jsonPlaceholderClient.getUserById(createPostDTO.getUserId());
            if (user == null) {
                throw new ApiCommunicationException("Communication with API failed.");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid userId: " + e.getMessage());
        }
        Post post = new Post();
        post.setUserId(createPostDTO.getUserId());
        post.setTitle(createPostDTO.getTitle());
        post.setBody(createPostDTO.getBody());
        return postRepository.save(post);
    }
    public List<Post> getPostsByUserId(Integer userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        if (posts.isEmpty()) {
            throw new IllegalArgumentException("Posts not found for user: " + userId);
        }
        return posts;
    }

    public Post getPostById(Integer id) {
        Optional<Post> post = postRepository.findById(id);

        // If the post is found locally, return it
        if (post.isPresent()) {
            return post.get();
        }

        // If the post is not found locally, fetch it from the external API
        Post externalPost = jsonPlaceholderClient.getPostById(id);

        if (externalPost != null) {
            postRepository.save(externalPost);
            return externalPost;
        }
        throw new ApiCommunicationException("Comunication with api failed.");
    }

    //just for testing purposes
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    //just for testing purposes

    public void deletePost(Integer id) {
        if (!postRepository.existsById(id)) {
            throw new IllegalArgumentException("Post not found or is already deleted");
        }
        postRepository.deleteById(id);
    }

    //just for testing purposes
    public void deleteAllPosts() {
        postRepository.deleteAll();
    }
    //just for testing purposes

    public Post updatePost(Integer id, UpdatePostDTO updatedPost) {
        Optional<Post> existingPost = postRepository.findById(id);
        if (existingPost.isPresent()) {
            Post post = existingPost.get();
            post.setTitle(updatedPost.getTitle());
            post.setBody(updatedPost.getBody());
            return postRepository.save(post);
        } else {
            throw new IllegalArgumentException("Post not found");
        }
    }

    private void checkId(Integer id){
        if (id > 100) {
            throw new IllegalArgumentException("Id is too large. Try a value not greater than 100.");
        }
        if (id < 1) {
            throw new IllegalArgumentException("Id is too low. Try a value greater than 0.");
        }
    }

    private void checkUserId(Integer userId){
        if (userId > 100) {
            throw new IllegalArgumentException("UserId is too large. Try a value not greater than 100.");
        }
        if (userId < 1) {
            throw new IllegalArgumentException("UserId is too low. Try a value greater than 0.");
        }
    }

    private void checkTitle(String title){
        if (title.length() > 250) {
            throw new IllegalArgumentException("Title is too long. Try decreasing characters below 250.");
        }
        if (title.length() < 3) {
            throw new IllegalArgumentException("Title is too short. Try adding at least 3 characters.");
        }
    }
    private void checkBody(String body){
        if (body.length() > 5000) {
            throw new IllegalArgumentException("Body is too long. Try decreasing characters below 5000.");
        }
        if (body.length() < 10) {
            throw new IllegalArgumentException("Body is too short. Try adding at least 10 characters.");
        }
    }

}