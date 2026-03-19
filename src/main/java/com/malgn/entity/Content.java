package com.malgn.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "contents")
@ToString(exclude = "member")
public class Content {

    @Builder
    public Content(Member member, String title, String description, String createdBy, Long viewCount) {
        this.member = member;
        this.title = title;
        this.description = description;
        this.createdBy = createdBy;
        this.viewCount = viewCount;
    }

    @Id @Column(name = "content_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long viewCount;

    private LocalDateTime createdDate;

    @Column(nullable = false, length = 50)
    private String createdBy;

    private LocalDateTime lastModifiedDate;

    @Column(length = 50)
    private String lastModifiedBy;

    @PrePersist
    public void setCreateDate() {
        this.createdDate = LocalDateTime.now();
        if (Objects.isNull(this.viewCount)) {
            this.viewCount = 0L;
        }
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    public void update(String title, String description, String lastModifiedBy) {
        this.title = title;
        this.description = description;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = LocalDateTime.now();
    }

}
