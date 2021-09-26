package de.tomze.backend.model;


import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name="blog_table")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogEntity {


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    @Id
    @GeneratedValue
    @Column(name = "blog_id", nullable = false)
    private Long blogId;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "entry", nullable = false)
    private String entry;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogEntity that = (BlogEntity) o;
        return Objects.equals(userId, that.userId) && blogId.equals(that.blogId) && date.equals(that.date) && entry.equals(that.entry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, blogId, date, entry);
    }
}
