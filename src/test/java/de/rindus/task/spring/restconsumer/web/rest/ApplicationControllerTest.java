package de.rindus.task.spring.restconsumer.web.rest;

import de.rindus.task.spring.restconsumer.MockUtils;
import de.rindus.task.spring.restconsumer.model.post.Post;
import de.rindus.task.spring.restconsumer.model.post.dto.PostDto;
import de.rindus.task.spring.restconsumer.service.RestService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(ApplicationController.class)
public class ApplicationControllerTest {
    @Autowired
    private WebTestClient webClient;
    
    @MockBean
    private RestService restService;
    
    @Test
    void testCreatePost(){
        PostDto inputDto =
            MockUtils.getPostDto();
        Post result = MockUtils.getPost();
        Mockito.when(restService.createPost(inputDto)).thenReturn(Mono.just(result));
        webClient.post().uri("/api/v1/post").bodyValue(result).exchange()
            .expectStatus().is2xxSuccessful()
            .expectBody(Post.class).isEqualTo(result);
    }
    
}
