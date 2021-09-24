package de.tomze.backend.image;

import de.tomze.backend.model.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;


@Entity
@Getter
@Setter
@Table(name = "image_table")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageEntity {

    @ManyToOne
    @JoinColumn(name = "id")
    private UserEntity id;

    @Id
    @GeneratedValue
    @Column(name = "image_id", nullable = false)
    Long imageId;

    @Lob
    byte[] content;

    @Column(name = "name", nullable = false)
    String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageEntity that = (ImageEntity) o;
        return id.equals(that.id) && imageId.equals(that.imageId) && Arrays.equals(content, that.content) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, imageId, name);
        result = 31 * result + Arrays.hashCode(content);
        return result;
    }
}
