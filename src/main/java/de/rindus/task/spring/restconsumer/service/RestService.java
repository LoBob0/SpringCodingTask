package de.rindus.task.spring.restconsumer.service;

import de.rindus.task.spring.restconsumer.model.post.dto.PostDto;
import de.rindus.task.spring.restconsumer.model.post.Post;
import org.springframework.core.io.Resource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RestService {
    
    Mono<Post> createPost(PostDto inputDto);
    
    Flux<Post> getPosts();
    
    Mono<Post> getPost(Long id);
    
    Mono<Post> updatePost(PostDto postDto);
    
    Mono<Post> patchPost(PostDto postDto);
    
    Mono<Object> deletePost(Long id);
    
    Mono<Resource> downloadPosts(String type);
}
