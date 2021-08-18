package de.rindus.task.spring.restconsumer.web.rest;

import de.rindus.task.spring.restconsumer.model.post.Post;
import de.rindus.task.spring.restconsumer.model.post.dto.PostDto;
import de.rindus.task.spring.restconsumer.service.RestService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/post")
@CrossOrigin("*")
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
    
    @PutMapping
    public Mono<Post> updatePost(@RequestBody PostDto postDto){
        return restService.updatePost(postDto);
    }
    
    @PatchMapping
    public Mono<Post> patchPost(@RequestBody PostDto postDto){
        return restService.patchPost(postDto);
    }
    
    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Post> getPosts(){
        return restService.getPosts();
    }
    
    @GetMapping("/{id}")
    public Mono<Post> getPost(@PathVariable Long id){
        return restService.getPost(id);
    }
    
    @DeleteMapping("/{id}")
    public Mono<Object> deletePost(@PathVariable Long id){
        return restService.deletePost(id);
    }
    
    
    @GetMapping("/download")
    public ResponseEntity<Mono<Resource>> downloadJson(@RequestParam(required = false) String type){
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=posts.json")
            .body(restService.downloadPosts(type));
    }
}
