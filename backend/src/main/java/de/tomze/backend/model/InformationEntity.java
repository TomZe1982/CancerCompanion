package de.tomze.backend.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name="info_table")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InformationEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "info", nullable = false)
    String info;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InformationEntity that = (InformationEntity) o;
        return id.equals(that.id) && info.equals(that.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, info);
    }
}
