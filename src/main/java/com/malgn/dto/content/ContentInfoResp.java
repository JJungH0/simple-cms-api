package com.malgn.dto.content;

import com.malgn.entity.Content;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ContentInfoResp {
    private Long id;
    private String title;
    private String description;
    private Long viewCount;
    private String createBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;

    public static ContentInfoResp from(Content content) {
        return new ContentInfoResp(
                content.getId(),
                content.getTitle(),
                content.getDescription(),
                content.getViewCount(),
                content.getCreatedBy(),
                content.getCreatedDate(),
                content.getLastModifiedBy(),
                content.getLastModifiedDate()
        );
    }
}
