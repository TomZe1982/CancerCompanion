package de.tomze.backend.controller;

import de.tomze.backend.api.BlogFromAppDto;
import de.tomze.backend.api.BlogToAppDto;
import de.tomze.backend.model.BlogEntity;

import java.util.ArrayList;
import java.util.List;

abstract class BlogControllerMapper {

    protected List<BlogToAppDto> mapBlogToAppDtoList (List<BlogEntity> blogEntityList) {

        List<BlogToAppDto> blogToAppDtoList = new ArrayList<>();

        for(BlogEntity blog : blogEntityList){
            BlogToAppDto blogToAppDto = mapBlogToAppDto(blog);
            blogToAppDtoList.add(blogToAppDto);

        }
        return(blogToAppDtoList);
    }

    protected BlogToAppDto mapBlogToAppDto (BlogEntity blogEntity){
        return BlogToAppDto.builder()
                .entry(blogEntity.getEntry())
                .date(blogEntity.getDate()).build();
    }

}
