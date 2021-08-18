package de.rindus.task.spring.restconsumer.utils;

import de.rindus.task.spring.restconsumer.model.post.Post;
import de.rindus.task.spring.restconsumer.model.post.dto.PostDto;
import de.rindus.task.spring.restconsumer.model.user.Address;
import de.rindus.task.spring.restconsumer.model.user.Company;
import de.rindus.task.spring.restconsumer.model.user.Geo;
import de.rindus.task.spring.restconsumer.model.user.User;

public class MockUtils {
    
    public static final long ONE = 1L;
    
    public static PostDto getPostDto() {
        return PostDto.builder().id(ONE).userId(ONE).title("The Title").body("A Body").build();
    }
    
    public static Post getPost() {
        return getPostDto().toModel( getUser() );
    }
    
    private static User getUser() {
        
        return User.builder().id(ONE).email("example@example.de").name("user")
            .phone("1-770-736-8031 x56442").website("www.example.de").address( getAddress() )
            .company( getCompany() ).build();
    }
    
    private static Company getCompany() {
        return Company.builder().name("company").catchPhrase("We're the company").bs("A bs").build();
    }
    
    private static Address getAddress() {
        return Address.builder().city("city").street("street").suite("42A").zipcode("42690").geo( getGeo() ).build();
    }
    
    private static Geo getGeo() {
        return Geo.builder().lat("42").lng("42").build();
    }
}
