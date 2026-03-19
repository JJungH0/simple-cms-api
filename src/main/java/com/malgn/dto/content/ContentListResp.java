package com.malgn.dto.content;

import com.malgn.entity.Content;

public record ContentListResp (
        Long id,
        String memberName,
        String title,
        Long viewCount
){
    public static ContentListResp from(Content content) {
        return new ContentListResp(
                content.getId(),
                content.getMember().getName(),
                content.getTitle(),
                content.getViewCount()
        );
    }
}
