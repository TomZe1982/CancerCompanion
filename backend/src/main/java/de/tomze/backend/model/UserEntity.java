package de.tomze.backend.model;


import lombok.*;

import javax.persistence.*;
import java.util.*;


@Entity
@Getter
@Setter
@Table(name="user_table")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "id")
    private List<BlogEntity> blogEntries = new ArrayList<>();

    public void addBlog(BlogEntity blogEntity) {
        blogEntries.add(blogEntity);
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

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(blogEntries, that.blogEntries)  && id.equals(that.id) && role.equals(that.role) && userName.equals(that.userName) && password.equals(that.password) && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blogEntries, id, role, userName, password, email);
    }
}
