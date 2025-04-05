package com.studycafe.User_Signup.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCreateForm {
    @Size(min = 3, max = 25)
    @NotEmpty(message = "ID 입력은 필수입니다.")
    private String username;

    @NotEmpty(message = "password 입력은 필수입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[!@#$%^&*]).+$",
            message = "비밀번호는 영문자와 특수문자를 포함해야 합니다.")
    private String password1;

    @NotEmpty(message = "password 확인은 필수입니다.")
    private String password2;

    @NotEmpty(message = "phoneNumber 입력은 필수입니다.")
    @Pattern(
            regexp = "^[0-9]+$",
            message = "'-'을 제외한 숫자만 입력해주세요."
    )
    private String phoneNumber;
}
