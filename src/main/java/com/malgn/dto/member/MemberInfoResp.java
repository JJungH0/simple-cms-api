package com.malgn.dto.member;

import com.malgn.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfoResp {
    private String name;
    private Role role;
}
