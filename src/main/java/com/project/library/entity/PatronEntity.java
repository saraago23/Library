package com.project.library.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "patrons")
public class PatronEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @OneToOne
    @PrimaryKeyJoinColumn
    private UserEntity user;
    @Column(name = "deleted")
    private Boolean deleted;
}
