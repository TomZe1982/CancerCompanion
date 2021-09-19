package de.tomze.backend.service;

import de.tomze.backend.api.BlogFromAppDto;
import de.tomze.backend.model.BlogEntity;
import de.tomze.backend.model.UserEntity;
import de.tomze.backend.repository.BlogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public BlogEntity getBlogEntry(String userName, Long blogId) {
        Optional<UserEntity> userEntityBlogEntryOptional = userService.getUser(userName);
        if(userEntityBlogEntryOptional.isEmpty()){
            throw new EntityNotFoundException("User not found");
        }

        UserEntity userEntityBlogEntry = userEntityBlogEntryOptional.get();

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
        Optional<UserEntity> userEntityOptional = userService.getUser(authUser.getUserName());
        if (userEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        UserEntity userEntityBlog = userEntityOptional.get();
        BlogEntity newBlogEntity = mapBlogEntity(blogFromAppDto);

        newBlogEntity.setId(userEntityBlog);
        userEntityBlog.addBlog(newBlogEntity);

        blogRepository.save(newBlogEntity);

        return newBlogEntity;
    }


/*    public List<BlogEntity> deleteBlog(UserEntity authUser) {
       Optional<UserEntity> userEntityOptionalDeleteBlog = userService.getUser(authUser.getUserName());
        if(userEntityOptionalDeleteBlog.isEmpty()){
            throw new IllegalArgumentException("User not found");
        }
        UserEntity userEntityDeleteBlog = userEntityOptionalDeleteBlog.get();

        return blogRepository.deleteAll(userEntityDeleteBlog.getId());
    }*/

    @Transactional
    public BlogEntity deleteBlogEntry(UserEntity authUser, Long blogId) {
        var user = userService.getUser(authUser.getUserName()).get();

        BlogEntity blogEntityDelete = getBlogEntry(authUser.getUserName(), blogId);

        user.getBlogEntries().remove(blogEntityDelete);
        return new BlogEntity();
    }

    public BlogEntity mapBlogEntity(BlogFromAppDto blogFromAppDto) {
        BlogEntity blogEntity = new BlogEntity();
        blogEntity.setEntry(blogFromAppDto.getEntry());
        blogEntity.setDate("today");

        return blogEntity;
    }



}
