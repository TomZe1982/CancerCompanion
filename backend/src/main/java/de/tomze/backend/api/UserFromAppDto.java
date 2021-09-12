package de.tomze.backend.api;

import de.tomze.backend.model.Address;
import lombok.*;

import javax.persistence.Embedded;


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
    @Embedded
    private Address address;
}
