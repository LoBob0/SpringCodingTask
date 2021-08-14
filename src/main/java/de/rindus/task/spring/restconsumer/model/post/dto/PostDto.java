package de.rindus.task.spring.restconsumer.model.post.dto;

import de.rindus.task.spring.restconsumer.model.post.Post;
import de.rindus.task.spring.restconsumer.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    private Long userId;
    private Long id;
    private String title;
    private String body;
    
    public Post toModel(User user){
        return Post.builder().id(id).title(title).body(body).user(user).build();
    }
}
