package de.tomze.backend.api;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserToAppDto {


    private String userName;

    private String password;
}
