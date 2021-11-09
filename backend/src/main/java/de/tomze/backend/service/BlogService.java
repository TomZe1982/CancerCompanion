package de.tomze.backend.service;

import de.tomze.backend.api.BlogFromAppDto;
import de.tomze.backend.model.BlogEntity;
import de.tomze.backend.model.UserEntity;
import de.tomze.backend.repository.BlogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.NotAuthorizedException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;



@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final UserService userService;

    public BlogService(BlogRepository blogRepository, UserService userService) {
        this.blogRepository = blogRepository;
        this.userService = userService;
    }

    public List<BlogEntity> getAllBlogs(String userName) {
        UserEntity userEntity = userService.getUser(userName);

        return userEntity.getBlogEntries();
    }

    public BlogEntity getBlogEntry(String userName, Long blogId) {

        UserEntity userEntityBlogEntry = userService.getUser(userName);

        List<BlogEntity> blogEntityList = userEntityBlogEntry.getBlogEntries();

        for(BlogEntity blogEntityFound : blogEntityList){
            Long blogIdFound = blogEntityFound.getBlogId();
            if(blogIdFound.equals(blogId)){


                return blogEntityFound;
            }
        }

        throw new EntityNotFoundException("No blog found");
    }

    public BlogEntity createBlog(UserEntity authUser, BlogFromAppDto blogFromAppDto) {

        UserEntity userEntityBlog = userService.getUser(authUser.getUserName());
        BlogEntity newBlogEntity = mapBlogEntity(blogFromAppDto);

        newBlogEntity.setUserId(userEntityBlog);
        userEntityBlog.addBlog(newBlogEntity);

        blogRepository.save(newBlogEntity);

        return newBlogEntity;
    }

    @Transactional
    public BlogEntity deleteBlogEntry(UserEntity authUser, String userName, Long blogId) {
        var user = userService.getUser(userName);

        BlogEntity blogEntityDelete = getBlogEntry(userName, blogId);

        if(authUser.getRole().equals("admin") && user.getRole().equals("admin") && !authUser.getUserName().equals(userName)){
            throw new NotAuthorizedException("Admin must not delete admins blog");
        }

        user.getBlogEntries().remove(blogEntityDelete);

        return new BlogEntity();
    }

    public BlogEntity mapBlogEntity(BlogFromAppDto blogFromAppDto) {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        String formatDateTime = now.format(formatter);

        BlogEntity blogEntity = new BlogEntity();
        blogEntity.setEntry(blogFromAppDto.getEntry());
        blogEntity.setDate(formatDateTime);

        return blogEntity;
    }


}
