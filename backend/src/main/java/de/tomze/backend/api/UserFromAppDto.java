package de.tomze.backend.api;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserFromAppDto {

    private String userName;
    private String password;
    private String secondName;
    private String firstName;
    private String email;
    private String street;
    private String number;
    private String city;
    private String zipCode;
}
