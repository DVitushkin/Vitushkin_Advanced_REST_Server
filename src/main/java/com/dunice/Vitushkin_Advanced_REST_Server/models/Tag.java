package com.dunice.Vitushkin_Advanced_REST_Server.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToMany(mappedBy = "tags")
    @Column(name = "news")
    private List<News> news;
}
