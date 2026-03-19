package com.malgn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member {

    @Id @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "member")
    private final List<Content> contents = new ArrayList<>();

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    @Builder
    public Member(String name, String password, Role role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    @PrePersist
    public void setCreateDate() {
        this.createdDate = LocalDateTime.now();
    }

    @PreUpdate
    public void setLastModifiedDate() {
        this.lastModifiedDate = LocalDateTime.now();
    }
}
