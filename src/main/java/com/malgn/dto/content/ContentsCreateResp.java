package com.malgn.dto.content;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ContentsCreateResp {
    private Long id;
    private String memberName;
    private String title;
    private Long count;
    private LocalDateTime createdDate;
}
