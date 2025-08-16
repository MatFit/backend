package com.aftermath.backend.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Check;

// Entities like user stored here
@Entity
@Table(name = "users")
public class User {
    private static final int MAXIMUM_USERNAME_LENGTH = 12;
    private static final int MINIMUM_PASSWORD_LENGTH = 12;

    @Id // Classify as unique ID
    @GeneratedValue(strategy = GenerationType.AUTO) // Generate unique ID most appropriate by JPA
    final private Long id;

    @Email
    private String email;

    @NotBlank
    @Column(length = MAXIMUM_USERNAME_LENGTH)
    private String username;

    @NotBlank
    @Size(min = MINIMUM_PASSWORD_LENGTH)
    private String password;

    // Skeleton Constructor
    protected User() { this.id = null; }
    // Parametrized Constructor
    public User(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }
    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail() {
        this.email = email;
    }

    public void setUsername() {
        this.username = username;
    }
}
