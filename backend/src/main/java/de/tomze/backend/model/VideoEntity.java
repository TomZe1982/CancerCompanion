package de.tomze.backend.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="video_table")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoEntity {

    @Id
    @GeneratedValue
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="vid_id", nullable = false, unique = true)
    private String vid_id;

}
