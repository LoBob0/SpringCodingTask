package de.rindus.task.spring.restconsumer.service.impl;

import de.rindus.task.spring.restconsumer.model.post.Post;
import de.rindus.task.spring.restconsumer.model.post.dto.PostDto;
import de.rindus.task.spring.restconsumer.model.user.User;
import de.rindus.task.spring.restconsumer.service.RestService;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class RestServiceImpl implements RestService {
    
    private final WebClient webClient;
    
    public RestServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }
    
    @Override
    public Mono<Post> createPost(PostDto inputDto) {
        final Mono<PostDto> postDtoMono = webClient.post()
            .uri("https://jsonplaceholder.typicode.com/posts/").bodyValue(inputDto).retrieve()
            .bodyToMono(PostDto.class).subscribeOn(
                Schedulers.boundedElastic());
        final Mono<User> userMono = webClient.get().uri(uriBuilder -> uriBuilder
            .path("https://jsonplaceholder.com/users/{id}").build(inputDto.getUserId())).retrieve()
            .bodyToMono(User.class).subscribeOn(
                Schedulers.boundedElastic());
        return Mono.zip(postDtoMono, userMono).map(p->p.getT1().toModel(p.getT2()));
    }
}
