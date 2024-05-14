package com.project.library.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="books")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private  Integer id;
    @Column(name="title")
    private String title;
    @Column(name = "author")
    private  String author;
    @Column(name = "publication_year")
    private Integer publicationYear;
    @Column(name = "isbn")
    private String isbn;
    @Column(name = "deleted")
    private Boolean deleted;

}
