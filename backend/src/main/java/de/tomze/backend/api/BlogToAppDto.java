package de.tomze.backend.api;

import lombok.*;



@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogToAppDto {

    private String date;

    private String entry;

}
