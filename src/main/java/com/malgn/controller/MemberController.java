package com.malgn.controller;

import com.malgn.dto.content.ContentListResp;
import com.malgn.dto.member.MemberCreateReq;
import com.malgn.dto.member.MemberInfoResp;
import com.malgn.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberInfoResp> createMember(@RequestBody @Valid MemberCreateReq req) {
        MemberInfoResp member = memberService.createMember(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

    @GetMapping("/me")
    public ResponseEntity<MemberInfoResp> getCurrentMember(Authentication authentication) {
        return ResponseEntity.ok(memberService.getCurrentMember(authentication.getName()));
    }

    @GetMapping("/{name}/contents")
    public ResponseEntity<Page<ContentListResp>> findMemberContents(@PathVariable String name,
                                                                    @PageableDefault(size = 10,sort = "createdDate", direction = Sort.Direction.DESC)
                                                              Pageable pageable) {
        return ResponseEntity.ok(memberService.findContentsByMember(name,pageable));
    }
}
