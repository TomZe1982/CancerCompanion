package de.tomze.backend.api;


import lombok.*;




@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFromAppDto {

    private String userName;
    private String password;
    private String email;

}
