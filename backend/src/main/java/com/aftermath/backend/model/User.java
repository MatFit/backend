package com.aftermath.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    private static final int MAXIMUM_USERNAME_LENGTH = 100;
    private static final int MINIMUM_PASSWORD_LENGTH = 12;
    private static final int MAXIMUM_EMAIL_LENGTH = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id;

    @Email
    @NotBlank
    @Column(name = "email", nullable = false, unique = true, length = MAXIMUM_EMAIL_LENGTH)
    private String email;

    @NotBlank
    @Column(name = "username", nullable = false, unique = true, length = MAXIMUM_USERNAME_LENGTH)
    private String username;

    @NotBlank
    @Size(min = MINIMUM_PASSWORD_LENGTH)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<QuizEntity> quizzes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MarketEntity> markets = new ArrayList<>();

    public User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Helper methods
    public void addQuiz(QuizEntity quiz) {
        quizzes.add(quiz);
        quiz.setUser(this);
    }

    public void removeQuiz(QuizEntity quiz) {
        quizzes.remove(quiz);
        quiz.setUser(null);
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<QuizEntity> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<QuizEntity> quizzes) {
        this.quizzes = quizzes;
    }
}