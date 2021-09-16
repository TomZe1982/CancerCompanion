package de.tomze.backend.api;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserToUpdateDto {

    private String userName;
    private String email;
}
