package de.tomze.backend.api;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private String kind;
    private String etag;
    private String id;

}
