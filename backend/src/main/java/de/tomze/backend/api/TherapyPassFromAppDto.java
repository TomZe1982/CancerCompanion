package de.tomze.backend.api;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TherapyPassFromAppDto {


    private String date;

    private String title;

    private String description;

}
