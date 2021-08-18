package de.rindus.task.spring.restconsumer.web.rest;

import static org.mockito.ArgumentMatchers.any;

import de.rindus.task.spring.restconsumer.utils.MockUtils;
import de.rindus.task.spring.restconsumer.model.post.Post;
import de.rindus.task.spring.restconsumer.model.post.dto.PostDto;
import de.rindus.task.spring.restconsumer.service.RestService;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(ApplicationController.class)
class ApplicationControllerTest {
    @Autowired
    private WebTestClient webClient;
    
    @MockBean
    private RestService restService;
    
    @Test
    void testCreatePost(){
        PostDto inputDto =
            MockUtils.getPostDto();
        Post result = MockUtils.getPost();
        Mockito.when(restService.createPost(any())).thenReturn(Mono.just(result));
        webClient.post().uri("/api/v1/post").bodyValue(result).exchange()
            .expectStatus().is2xxSuccessful()
            .expectBody(Post.class).isEqualTo(result);
    }
    
    @Test
    void testUpdatePost(){
        PostDto inputDto =
            MockUtils.getPostDto();
        Post result = MockUtils.getPost();
        Mockito.when(restService.updatePost(any())).thenReturn(Mono.just(result));
        webClient.put().uri("/api/v1/post").bodyValue(result).exchange()
            .expectStatus().is2xxSuccessful()
            .expectBody(Post.class).isEqualTo(result);
    }
    
    @Test
    void testPatchPost(){
        PostDto inputDto =
            MockUtils.getPostDto();
        Post result = MockUtils.getPost();
        Mockito.when(restService.patchPost(any())).thenReturn(Mono.just(result));
        webClient.patch().uri("/api/v1/post").bodyValue(result).exchange()
            .expectStatus().is2xxSuccessful()
            .expectBody(Post.class).isEqualTo(result);
    }
    
    @Test
    void testGetPost(){
        PostDto inputDto =
            MockUtils.getPostDto();
        Post result = MockUtils.getPost();
        Mockito.when(restService.getPost(1L)).thenReturn(Mono.just(result));
        webClient.get().uri(uriBuilder -> uriBuilder.path("/api/v1/post/{id}").build(1L)).exchange()
            .expectStatus().is2xxSuccessful()
            .expectBody(Post.class).isEqualTo(result);
    }
    
    @Test
    void testDeletePost(){
        PostDto inputDto =
            MockUtils.getPostDto();
        Post result = MockUtils.getPost();
        Mockito.when(restService.deletePost(1L)).thenReturn(Mono.just(result));
        webClient.get().uri(uriBuilder -> uriBuilder.path("/api/v1/post/{id}").build(1L)).exchange()
            .expectStatus().is2xxSuccessful();
    }
    
    @Test
    void testGetPosts(){
        final List<Post> collect = List.of(1, 2, 3, 4, 5).stream().map(i -> MockUtils.getPost())
            .collect(Collectors.toList());
        Mockito.when(restService.getPosts()).thenReturn(Flux.fromIterable(collect));
        webClient.get().uri("/api/v1/post").exchange()
            .expectStatus().is2xxSuccessful()
            .expectBodyList(Post.class).hasSize(5);
    }
    
    
    @Test
    void testDownloadPosts(){
        final List<Post> collect = List.of(1, 2, 3, 4, 5).stream().map(i -> MockUtils.getPost())
            .collect(Collectors.toList());
        Mockito.when(restService.downloadPosts(any())).thenReturn(Mono.just(new ByteArrayResource(new byte[0])));
        webClient.get().uri("/api/v1/post/download").exchange()
            .expectStatus().is2xxSuccessful()
            .expectHeader().valueEquals("Content-Disposition","attachment; filename=posts.json");
    }
}
