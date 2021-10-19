package de.tomze.backend.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name="therapy_pass_table")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TherapyPassEntity implements Comparable<TherapyPassEntity> {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    @Id
    @GeneratedValue
    @Column(name = "therapy_pass_id", nullable = false)
    private Long therapyId;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Override
    public int compareTo(TherapyPassEntity o) {
        return getDate().compareTo(o.getDate());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TherapyPassEntity that = (TherapyPassEntity) o;
        return Objects.equals(userId, that.userId) && therapyId.equals(that.therapyId) && date.equals(that.date) && title.equals(that.title) && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, therapyId, date, title, description);
    }
}
