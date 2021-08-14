package de.rindus.task.spring.restconsumer.web.rest;

import de.rindus.task.spring.restconsumer.model.post.Post;
import de.rindus.task.spring.restconsumer.model.post.dto.PostDto;
import de.rindus.task.spring.restconsumer.service.RestService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/post")
public class ApplicationController {
    private final RestService restService;
    
    public ApplicationController(RestService restService) {
        this.restService = restService;
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Post> createPost(@RequestBody PostDto postDto){
        return restService.createPost(postDto);
    }
}
