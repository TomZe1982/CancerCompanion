package de.tomze.backend.service;

import de.tomze.backend.api.BlogFromAppDto;
import de.tomze.backend.model.BlogEntity;
import de.tomze.backend.model.UserEntity;
import de.tomze.backend.repository.BlogRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final UserService userService;

    public BlogService(BlogRepository blogRepository, UserService userService) {
        this.blogRepository = blogRepository;
        this.userService = userService;
    }

    public List<BlogEntity> getAllBlogs() {
        return blogRepository.findAll();
    }

    public BlogEntity createBlog(UserEntity authUser, BlogFromAppDto blogFromAppDto) {
        Optional<UserEntity> userEntityOptional = userService.getUser(authUser.getUserName());
        if(userEntityOptional.isEmpty()){
            throw new EntityNotFoundException("User not found");
        }
        UserEntity userEntityBlog = userEntityOptional.get();
        BlogEntity newBlogEntity = mapBlogEntity(blogFromAppDto);

        newBlogEntity.setId(userEntityBlog);
        userEntityBlog.addBlog(newBlogEntity);

        blogRepository.save(newBlogEntity);

        return newBlogEntity;
    }


    public BlogEntity mapBlogEntity (BlogFromAppDto blogFromAppDto) {
        BlogEntity blogEntity = new BlogEntity();
        blogEntity.setEntry(blogFromAppDto.getEntry());
        blogEntity.setDate("today");

        return blogEntity;
    }

}
