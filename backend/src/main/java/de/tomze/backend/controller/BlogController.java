package de.tomze.backend.controller;


import de.tomze.backend.api.BlogToAppDto;
import de.tomze.backend.model.BlogEntity;
import de.tomze.backend.model.UserEntity;
import de.tomze.backend.service.BlogService;
import de.tomze.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@CrossOrigin
@RequestMapping("api/tomze/blog")
public class BlogController extends BlogControllerMapper {

    private final BlogService blogService;
    private final UserService userService;

    public BlogController(BlogService blogService, UserService userService) {
        this.blogService = blogService;
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<BlogToAppDto>> getAllBlogs() {

        List<BlogEntity> blogEntityList = blogService.getAllBlogs();

        List<BlogToAppDto> blogToAppDtoList = mapBlogToAppDtoList(blogEntityList);

        return ok(blogToAppDtoList);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<List<BlogToAppDto>> getUserBlog(@PathVariable String userName) {
        Optional<UserEntity> fetchedUserEntity = userService.getUser(userName);

        if (fetchedUserEntity.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        UserEntity userEntityBlog = fetchedUserEntity.get();

        List<BlogEntity> blogEntityList = userEntityBlog.getBlogEntries();


        List<BlogToAppDto> blogToAppDtoList = mapBlogToAppDtoList(blogEntityList);
        return ok(blogToAppDtoList);

    }


}
