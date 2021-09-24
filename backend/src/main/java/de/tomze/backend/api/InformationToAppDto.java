package de.tomze.backend.api;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InformationToAppDto {

    private Long id;

    private String title;

    private String info;

}
