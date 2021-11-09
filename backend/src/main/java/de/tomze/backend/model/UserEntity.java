package de.tomze.backend.model;


import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "userId")
    private List<BlogEntity> blogEntries = new ArrayList<>();

    public void addBlog(BlogEntity blogEntity) {
        blogEntries.add(blogEntity);
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "userId")
    private List<TherapyPassEntity> therapyEntries = new ArrayList<>();

    public void addTherapy(TherapyPassEntity therapyPassEntity){
        therapyEntries.add(therapyPassEntity);
    }

    @Id
    @GeneratedValue
    @Column(name="user_id", nullable = false)
    private Long userId;

    @Column(name="role", nullable = false)
    private String role;

    @Column(name="userName", nullable = false, unique = true)
    private String userName;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name="avatar")
    private String avatar;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(blogEntries, that.blogEntries) && Objects.equals(therapyEntries, that.therapyEntries)  && userId.equals(that.userId) && role.equals(that.role) && userName.equals(that.userName) && password.equals(that.password) && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blogEntries, therapyEntries, userId, role, userName, password, email);
    }
}
