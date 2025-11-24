package com.aftermath.backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String content;

    @Column(nullable = false)
    private String url;

    // Optional
    private String imageUrl;

    @Column(nullable = false)
    private LocalDateTime publishedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id", nullable = false)
    private MarketEntity market;
}
