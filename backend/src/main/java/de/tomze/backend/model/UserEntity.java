package de.tomze.backend.model;


import lombok.*;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Getter
@Setter
@Table(name="cc_db")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Embedded
    private Address address;

    public Address getAddress() {
        return address;
    }

    @Id
    @GeneratedValue
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="role", nullable = false)
    private String role;

    @Column(name="userName", nullable = false, unique = true)
    private String userName;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="secondName", nullable = false)
    private String secondName;

    @Column(name="firstName", nullable = false)
    private String firstName;

    @Column(name="email", nullable = false, unique = true)
    private String email;

   /* @Column(name="street")
    private String street;

    @Column(name="number")
    private String number;

    @Column(name="city")
    private String city;

    @Column(name="zipCode")
    private String zipCode;*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id.equals(that.id) && role.equals(that.role) && userName.equals(that.userName) && password.equals(that.password) && secondName.equals(that.secondName) && firstName.equals(that.firstName) && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, userName, password, secondName, firstName, email);
    }
}
