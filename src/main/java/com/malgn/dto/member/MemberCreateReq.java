package com.malgn.dto.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class MemberCreateReq {
    @Size(max = 50,message = "사용자 이름은 50자를 초과할 수 없습니다.")
    @NotBlank(message = "사용자 이름을 입력해주세요")
    private String name;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    @Pattern(
            regexp = "^(?=.*[^a-zA-Z0-9]).+$",
            message = "비밀번호는 특수문자를 포함해야 합니다."
    )
    private String password;
}