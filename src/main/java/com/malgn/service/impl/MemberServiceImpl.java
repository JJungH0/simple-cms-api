package com.malgn.service.impl;

import com.malgn.dto.content.ContentListResp;
import com.malgn.dto.member.MemberCreateReq;
import com.malgn.dto.member.MemberInfoResp;
import com.malgn.entity.Member;
import com.malgn.entity.Role;
import com.malgn.exception.DuplicateMemberException;
import com.malgn.exception.MemberNotFoundException;
import com.malgn.repository.ContentRepository;
import com.malgn.repository.MemberRepository;
import com.malgn.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ContentRepository contentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberInfoResp createMember(MemberCreateReq req) {

        if (memberRepository.existsByName(req.getName())) {
            throw new DuplicateMemberException("중복된 사용자 이름입니다.");
        }

        Member member = Member.builder()
                .name(req.getName())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(Role.USER)
                .build();

        Member saved = memberRepository.save(member);

        return new MemberInfoResp(saved.getName(), saved.getRole());
    }

    @Override
    @Transactional(readOnly = true)
    public MemberInfoResp getCurrentMember(String memberName) {
        Member member = memberRepository.findByName(memberName)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        return new MemberInfoResp(member.getName(), member.getRole());
    }


    @Override
    @Transactional(readOnly = true)
    public Page<ContentListResp> findContentsByMember(String memberName, Pageable pageable) {

        Member member = memberRepository.findByName(memberName)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        return contentRepository.findByMember(member, pageable)
                .map(ContentListResp::from);
    }
}
