package de.rindus.task.spring.restconsumer.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.xml.XmlMapper;
import de.rindus.task.spring.restconsumer.model.post.Post;
import de.rindus.task.spring.restconsumer.model.post.dto.PostDto;
import de.rindus.task.spring.restconsumer.utils.MockUtils;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RestServiceTest {
    @Autowired
    RestService restService;
    ObjectMapper objectMapper = new ObjectMapper();
    XmlMapper xmlMapper = new XmlMapper();
    
    
    @Test
    void testCreatePost(){
        final PostDto postDto = MockUtils.getPostDto();
        final Mono<Post> post = restService.createPost(postDto);
        StepVerifier.create(post).assertNext(p -> assertTrue(p.getBody().equals(postDto.getBody()) && p.getTitle().equals(postDto.getTitle()) && p.getUser().getId().equals(postDto.getUserId())) ).expectComplete();
    }
    
    
    @Test
    void testGetPosts(){
        final Flux<Post> post = restService.getPosts();
        StepVerifier.create(post).thenConsumeWhile(p-> p.getBody() != null && p.getTitle() != null && p.getUser().getId() != null).expectComplete();
    }
    
    @Test
    void testGetPost(){
        final Mono<Post> post = restService.getPost(1L);
        StepVerifier.create(post).consumeNextWith(p-> assertTrue(p.getBody() != null && p.getTitle() != null && p.getUser().getId() != null)).expectComplete();
    }
    
    @Test
    void testUpdatePost(){
        final PostDto postDto = MockUtils.getPostDto();
        final Mono<Post> post = restService.updatePost(postDto);
        StepVerifier.create(post).consumeNextWith(p-> assertTrue(p.getBody().equals(postDto.getBody()) && p.getTitle().equals(postDto.getTitle())&& p.getUser().getId().equals(postDto.getUserId()))).expectComplete();
    }
    
    @Test
    void testPatchPost(){
        final PostDto postDto = MockUtils.getPostDto();
        postDto.setTitle(null);
        final Mono<Post> post = restService.patchPost(postDto);
        StepVerifier.create(post).consumeNextWith(p-> assertTrue(p.getBody().equals(postDto.getBody()) && p.getTitle()!= null && p.getUser().getId().equals(postDto.getUserId()))).expectComplete();
    }
    
    @Test
    void testDownloadPostsJson(){
        final Mono<Resource> post = restService.downloadPosts("JSON");
        StepVerifier.create(post).consumeNextWith(p->{
            consumeJsonFile(p);
        }).expectComplete();
    }
    
    @Test
    void testDownloadPostsXML(){
        final Mono<Resource> post = restService.downloadPosts("XML");
        StepVerifier.create(post).consumeNextWith(p->{
            consumeXmlFile(p);
        }).expectComplete();
    }
    
    private void consumeXmlFile(Resource p)  {
        try {
            final ByteBuffer dst = ByteBuffer.allocate(2000);
            p.readableChannel().read(dst);
            final TypeReference<List<Post>> typeReference = new TypeReference<>() {
            };
            final Post[] posts = xmlMapper
                .readValue(dst.array(), Post[].class);
            assertNotEquals(0,posts.length);
        }
        catch (IOException ioException){
            fail("IOException Throwed");
        }
    }
    
    private void consumeJsonFile(Resource p) {
        try {
            final ByteBuffer dst = ByteBuffer.allocate(2000);
            p.readableChannel().read(dst);
            final List<Post> posts = objectMapper
                .readValue(dst.array(), new TypeReference<List<Post>>(){});
            assertFalse(posts.isEmpty());
        }
        catch (IOException ioException){
            fail("IOException Throwed");
        }
    }
    
}
