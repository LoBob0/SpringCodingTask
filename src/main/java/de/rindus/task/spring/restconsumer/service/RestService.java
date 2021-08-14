package de.rindus.task.spring.restconsumer.service;

import de.rindus.task.spring.restconsumer.model.post.dto.PostDto;
import de.rindus.task.spring.restconsumer.model.post.Post;
import reactor.core.publisher.Mono;

public interface RestService {
    
    Mono<Post> createPost(PostDto inputDto);
}
