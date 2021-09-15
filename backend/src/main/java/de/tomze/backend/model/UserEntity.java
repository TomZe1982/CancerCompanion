package de.tomze.backend.model;


import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name="cc_db")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {


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

    @Column(name="email", nullable = false, unique = true)
    private String email;

}
