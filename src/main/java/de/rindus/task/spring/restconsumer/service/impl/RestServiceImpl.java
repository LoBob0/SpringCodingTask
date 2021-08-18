package de.rindus.task.spring.restconsumer.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.xml.XmlMapper;
import de.rindus.task.spring.restconsumer.model.post.Post;
import de.rindus.task.spring.restconsumer.model.post.dto.PostDto;
import de.rindus.task.spring.restconsumer.model.user.User;
import de.rindus.task.spring.restconsumer.service.RestService;
import java.io.IOException;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Log4j2
public class RestServiceImpl implements RestService {
    
    public static final String JSONPLACEHOLDER_TYPICODE_COM = "jsonplaceholder.typicode.com/";
    public static final String JSONPLACEHOLDERS_USERS = JSONPLACEHOLDER_TYPICODE_COM + "users/";
    public static final String JSONPLACEHOLDER_POSTS = JSONPLACEHOLDER_TYPICODE_COM + "posts/";
    public static final String ID = "{id}";
    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final XmlMapper xmlMapper = new XmlMapper();
    
    public RestServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }
    
    @Override
    public Mono<Post> createPost(PostDto inputDto) {
        final Mono<PostDto> postDtoMono = webClient.post()
            .uri(JSONPLACEHOLDER_POSTS).bodyValue(inputDto).retrieve()
            .bodyToMono(PostDto.class).subscribeOn(
                Schedulers.boundedElastic());
        final Mono<User> userMono = webClient.get().uri(uriBuilder -> uriBuilder
            .path(JSONPLACEHOLDERS_USERS + "{id}").build(inputDto.getUserId())).retrieve()
            .bodyToMono(User.class).subscribeOn(
                Schedulers.boundedElastic());
        return Mono.zip(postDtoMono, userMono).map(p->p.getT1().toModel(p.getT2()));
    }
    
    @Override
    public Flux<Post> getPosts() {
        final Flux<PostDto> postDtoFlux = getPostDtoFlux();
        final Mono<List<User>> userFlux =
            getUserFlux();
        return userFlux.flatMapMany(u-> postDtoFlux.handle((p,sink)->{
            final Post post = p.toModel(
                u.stream().filter(us -> us.getId().equals(p.getUserId())).findFirst().orElse(null));
            sink.next(post);
        }));
    }
    
    @Override
    public Mono<Post> getPost(Long id) {
        return getPostDto(id).zipWhen(postDto -> getUser(postDto.getUserId()),
            (first,second)->first.toModel(second));
    }
    
    @Override
    public Mono<Post> updatePost(PostDto postDto) {
        final Mono<PostDto> postDtoMono = webClient.put()
            .uri(JSONPLACEHOLDER_POSTS).bodyValue(postDto).retrieve()
            .bodyToMono(PostDto.class).subscribeOn(
                Schedulers.boundedElastic());
        final Mono<User> userMono = webClient.get().uri(uriBuilder -> uriBuilder
            .path(JSONPLACEHOLDERS_USERS + ID).build(postDto.getUserId())).retrieve()
            .bodyToMono(User.class).subscribeOn(
                Schedulers.boundedElastic());
        return Mono.zip(postDtoMono, userMono).map(p->p.getT1().toModel(p.getT2()));
    }
    
    @Override
    public Mono<Post> patchPost(PostDto postDto) {
        final Mono<PostDto> postDtoMono = webClient.patch()
            .uri(JSONPLACEHOLDER_POSTS).bodyValue(postDto).retrieve()
            .bodyToMono(PostDto.class).subscribeOn(
                Schedulers.boundedElastic());
        final Mono<User> userMono = webClient.get().uri(uriBuilder -> uriBuilder
            .path(JSONPLACEHOLDERS_USERS + ID).build(postDto.getUserId())).retrieve()
            .bodyToMono(User.class).subscribeOn(
                Schedulers.boundedElastic());
        return Mono.zip(postDtoMono, userMono).map(p->p.getT1().toModel(p.getT2()));
    }
    
    @Override
    public Mono<Object> deletePost(Long id) {
        return webClient.delete()
            .uri(uriBuilder -> uriBuilder.path(JSONPLACEHOLDER_POSTS+ID).build(id)).retrieve()
            .bodyToMono(Object.class).subscribeOn(
                Schedulers.boundedElastic());
    }
    
    @Override
    public Mono<Resource> downloadPosts(String type) {
        return getPosts().collectList().map(p-> getByteArrayResource(p, type));
    }
    
    private ByteArrayResource getByteArrayResource(List<Post> p, String type) {
        byte[] bytes;
        if(type != null && type.equalsIgnoreCase("XML")){
            try {
                bytes = xmlMapper.writeValueAsBytes(p);
            } catch (IOException e) {
                bytes = new byte[0];
            }
        }else {
            try {
                bytes = objectMapper.writeValueAsBytes(p);
            } catch (JsonProcessingException e) {
                bytes = new byte[0];
            }
        }
        return new ByteArrayResource(bytes);
    }
    
    @Cacheable("posts")
    private Flux<PostDto> getPostDtoFlux() {
        return webClient.get()
            .uri(JSONPLACEHOLDER_TYPICODE_COM + "posts/").retrieve()
            .bodyToFlux(PostDto.class).subscribeOn(
                Schedulers.boundedElastic());
    }
    
    @Cacheable("posts")
    private Mono<PostDto> getPostDto(Long id) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder.path(JSONPLACEHOLDER_POSTS+ID).build(id)  ).retrieve()
            .bodyToMono(PostDto.class);
    }
    
    @Cacheable("users")
    private Mono<List<User>> getUserFlux() {
        return webClient.get().uri(JSONPLACEHOLDERS_USERS).retrieve()
            .bodyToFlux(User.class).subscribeOn(
                Schedulers.boundedElastic()).collectList();
    }
    @Cacheable("posts")
    private Mono<User> getUser(Long id) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder.path(JSONPLACEHOLDERS_USERS+ID).build(id)  ).retrieve()
            .bodyToMono(User.class).subscribeOn(
                Schedulers.boundedElastic());
    }
}
