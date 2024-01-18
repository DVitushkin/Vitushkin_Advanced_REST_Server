package com.dunice.Vitushkin_Advanced_REST_Server.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "news_id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "news_tags",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @Column(name = "tags")
    private List<Tag> tags;

    @Column(name = "title")
    private String title;
}
