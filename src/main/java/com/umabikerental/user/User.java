package com.umabikerental.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @SequenceGenerator(
            name = "user_id_sequence",
            sequenceName = "user_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_id_sequence"
    )
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonProperty("isAdmin") // Map isAdmin field to "admin" JSON property
    private boolean isAdmin;

    private String phoneNumber;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime created_at;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime modified_at;

    public User() {}

    public User(Long id, String name, String email, String username, Role role, boolean isAdmin, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.role = role;
        this.isAdmin = isAdmin;
        this.phoneNumber = phoneNumber;
    }
}
