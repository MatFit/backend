package com.aftermath.backend.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class MarketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ticker;

    @Column(nullable = false)
    private String marketName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleEntity> articles = new ArrayList<>();
}
