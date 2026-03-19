package com.malgn.repository;

import com.malgn.entity.Content;
import com.malgn.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
    Page<Content> findByMember(Member member, Pageable pageable);
}
