package de.tomze.backend.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="blog_db")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "blogText", nullable = false)
    private String blogText;


}
