package de.tomze.backend.service;

import de.tomze.backend.model.BlogEntity;
import de.tomze.backend.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public List<BlogEntity> getAllBlogs() {
        return blogRepository.findAll();
    }

 /*   public List<BlogEntity> getUserBlogs(Long id) {
        return blogRepository.findAllById(id);
    }*/
}
