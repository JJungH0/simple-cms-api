package com.malgn.service;

import com.malgn.dto.content.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface ContentsService {

    ContentsCreateResp createContent(ContentsCreateReq req, String memberName);

    Page<ContentListResp> findAll(Pageable pageable);

    ContentInfoResp getContentById(Long id);

    ContentInfoResp updateContent(Long id, ContentUpdateReq req, Authentication authentication);

    void deleteContent(Long id, Authentication authentication);
}
