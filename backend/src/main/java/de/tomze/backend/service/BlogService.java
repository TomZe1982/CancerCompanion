package de.tomze.backend.service;

import de.tomze.backend.api.BlogFromAppDto;
import de.tomze.backend.model.BlogEntity;
import de.tomze.backend.model.UserEntity;
import de.tomze.backend.repository.BlogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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

        newBlogEntity.setId(userEntityBlog);
        userEntityBlog.addBlog(newBlogEntity);

        blogRepository.save(newBlogEntity);

        return newBlogEntity;
    }

    public BlogEntity updateBlog(UserEntity authUser, BlogFromAppDto blogFromAppDto, Long blogId) {

        BlogEntity blogEntityUpdate = getBlogEntry(authUser.getUserName(), blogId);

        blogEntityUpdate.setEntry(blogFromAppDto.getEntry());

        return blogRepository.save(blogEntityUpdate);
    }

    @Transactional
    public List<BlogEntity> deleteBlog(UserEntity authUser) {
        List<BlogEntity> blogEntityListDelete = getAllBlogs(authUser.getUserName());

        for(BlogEntity blogEntityDelete : blogEntityListDelete){
            var user = userService.getUser(authUser.getUserName());
            user.getBlogEntries().remove(blogEntityDelete);
        }

        return new ArrayList<>();
    }

    @Transactional
    public BlogEntity deleteBlogEntry(UserEntity authUser, Long blogId) {
        var user = userService.getUser(authUser.getUserName());

        BlogEntity blogEntityDelete = getBlogEntry(authUser.getUserName(), blogId);

        user.getBlogEntries().remove(blogEntityDelete);

        return new BlogEntity();
    }

    public BlogEntity mapBlogEntity(BlogFromAppDto blogFromAppDto) {
        BlogEntity blogEntity = new BlogEntity();
        blogEntity.setEntry(blogFromAppDto.getEntry());
        blogEntity.setDate(LocalDateTime.now().toString());

        return blogEntity;
    }


}
