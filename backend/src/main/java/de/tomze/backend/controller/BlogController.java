package de.tomze.backend.controller;


import de.tomze.backend.api.BlogFromAppDto;
import de.tomze.backend.api.BlogToAppDto;
import de.tomze.backend.model.BlogEntity;
import de.tomze.backend.model.UserEntity;
import de.tomze.backend.service.BlogService;
import de.tomze.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.NotAuthorizedException;
import java.util.List;


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

    @GetMapping("/allblogs/{userName}")
    public ResponseEntity<List<BlogToAppDto>> getAllBlogs(@PathVariable String userName) {

        List<BlogEntity> blogEntityList = blogService.getAllBlogs(userName);

        List<BlogToAppDto> blogToAppDtoList = mapBlogToAppDtoList(blogEntityList);

        return ok(blogToAppDtoList);
    }

    @GetMapping("/{userName}/{blogId}")
    public ResponseEntity<BlogToAppDto> getBlogEntry (@PathVariable String userName, @PathVariable Long blogId){

        BlogEntity blogEntityFound = blogService.getBlogEntry(userName, blogId);

        BlogToAppDto blogToAppDtoFound = mapBlogToAppDto(blogEntityFound);

        return ok(blogToAppDtoFound);
    }


    @PostMapping("/newblog")
    public ResponseEntity<BlogToAppDto> createBlog(@AuthenticationPrincipal UserEntity authUser, @RequestBody BlogFromAppDto blogFromAppDto) {

        BlogEntity newBlogEntry = blogService.createBlog(authUser, blogFromAppDto);

        BlogToAppDto newBlogToAppDto = mapBlogToAppDto(newBlogEntry);

        return ok(newBlogToAppDto);

    }

    @DeleteMapping("/delete/{userName}/{blogId}")
    public ResponseEntity<BlogToAppDto> deleteBlogEntry(@AuthenticationPrincipal UserEntity authUser, @PathVariable String userName, @PathVariable Long blogId){

        if(authUser.getRole().equals("user") && !authUser.getUserName().equals(userName)){
            throw new NotAuthorizedException("User must not delete another user");
        }

        BlogEntity blogEntityDelete = blogService.deleteBlogEntry(authUser, userName, blogId);

        BlogToAppDto blogToAppDtoDelete = mapBlogToAppDto(blogEntityDelete);

        return ok(blogToAppDtoDelete);
    }

}
