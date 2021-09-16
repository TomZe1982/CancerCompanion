package de.tomze.backend.api;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YoutubeApiDto {

    private String etag;
    private String kind;
    private List<Item> items = new ArrayList<>();

    }


