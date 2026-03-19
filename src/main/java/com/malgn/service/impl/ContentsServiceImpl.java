package com.malgn.service.impl;

import com.malgn.dto.content.*;
import com.malgn.entity.Content;
import com.malgn.entity.Member;
import com.malgn.exception.ContentAccessDeniedException;
import com.malgn.exception.ContentNotFoundException;
import com.malgn.exception.MemberNotFoundException;
import com.malgn.repository.ContentRepository;
import com.malgn.repository.MemberRepository;
import com.malgn.service.ContentsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ContentsServiceImpl implements ContentsService {

    private final MemberRepository memberRepository;
    private final ContentRepository contentRepository;

    @Override
    public ContentsCreateResp createContent(ContentsCreateReq req, String memberName) {
        Member member = memberRepository
                .findByName(memberName).orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        Content content = Content.builder()
                .title(req.getTitle())
                .member(member)
                .description(req.getDescription())
                .createdBy(memberName)
                .build();

        Content saved = contentRepository.save(content);

        return new ContentsCreateResp(saved.getId(),
                saved.getCreatedBy(),
                saved.getTitle(),
                saved.getViewCount(),
                saved.getCreatedDate());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContentListResp> findAll(Pageable pageable) {
        return contentRepository.findAll(pageable)
                .map(ContentListResp::from);
    }

    @Override
    public ContentInfoResp getContentById(Long id) {
        Content content = contentRepository.findById(id).orElseThrow(() -> new ContentNotFoundException("해당 게시글은 존재하지 않습니다."));
        log.info("상세 조회 전 조회수:{}",content.getViewCount());
        content.increaseViewCount();
        log.info("상세 조회 후 조회수:{}",content.getViewCount());
        return ContentInfoResp.from(content);
    }

    @Override
    public ContentInfoResp updateContent(Long id, ContentUpdateReq req, Authentication authentication) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new ContentNotFoundException("해당 게시글은 존재하지 않습니다."));

        validateMemberRole(content, authentication);

        content.update(req.getTitle(), req.getDescription(), authentication.getName());

        return ContentInfoResp.from(content);
    }

    @Override
    public void deleteContent(Long id, Authentication authentication) {
        Content content = contentRepository.findById(id).orElseThrow(() -> new ContentNotFoundException("해당 게시글은 존재하지 않습니다."));
        validateMemberRole(content, authentication);
        contentRepository.delete(content);
    }

    private void validateMemberRole(Content content, Authentication authentication) {

        boolean isOwner = content.getMember().getName().equals(authentication.getName());
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        log.info("isOwner = {}", isOwner);
        log.info("isAdmin = {}", isAdmin);
        if (!isOwner && !isAdmin) {
            throw new ContentAccessDeniedException("해당 콘텐츠를 수정 또는 삭제할 권한이 존재하지 않습니다.");
        }
    }

}
