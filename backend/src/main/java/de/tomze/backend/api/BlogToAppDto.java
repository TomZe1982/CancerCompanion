package de.tomze.backend.api;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogToAppDto {

    private Long blogId;

    private String date;

    private String entry;

}
