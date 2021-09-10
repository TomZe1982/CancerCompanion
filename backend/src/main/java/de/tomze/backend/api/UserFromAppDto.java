package de.tomze.backend.api;

import lombok.*;



@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFromAppDto {

    private String role;
    private String userName;
    private String password;
    private String secondName;
    private String firstName;
    private String email;
    private String street;
    private String number;
    private String zipCode;
    private String city;
}
