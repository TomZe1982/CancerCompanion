package de.tomze.backend.api;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserToAppDto {

    private Long id;

    private String role;

    private String userName;

    private String password;

    private String email;

    private String avatar;

}
