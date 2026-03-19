package com.malgn.service;

import com.malgn.dto.content.ContentListResp;
import com.malgn.dto.member.MemberCreateReq;
import com.malgn.dto.member.MemberInfoResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberService {

    MemberInfoResp createMember(MemberCreateReq req);

    MemberInfoResp getCurrentMember(String memberName);

    Page<ContentListResp> findContentsByMember(String memberName, Pageable pageable);

}
