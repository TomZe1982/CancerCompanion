package de.tomze.backend.model;


import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
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
    @JoinColumn(name = "id")
    private UserEntity id;

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
        return Objects.equals(id, that.id) && blogId.equals(that.blogId) && date.equals(that.date) && entry.equals(that.entry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, blogId, date, entry);
    }
}
