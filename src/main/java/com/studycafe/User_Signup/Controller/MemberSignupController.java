package com.studycafe.User_Signup.Controller;

import com.studycafe.User_Signup.Service.MemberSignupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.studycafe.User_Signup.Model.MemberCreateForm;

@RequiredArgsConstructor // MemberService에 대한 기본 생성자
@Controller
@RequestMapping("/user")
public class MemberSignupController {

    private final MemberSignupService memberSignupService;
    //회원가입 화면으로 이동
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("memberCreateForm", new MemberCreateForm());
        return "user_signup";
    }
    @PostMapping("/signup") //get은 자원을 가져오는 역할, post는 자원을 넣어주는 역할을 한다
    public String signup(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "user_signup";
        }
        if(!memberCreateForm.getPassword1().equals(memberCreateForm.getPassword2())){
            bindingResult.rejectValue("password2", "passwordInCorrect","2개의 비밀번호가 일치하지 않습니다.");
            return "user_signup";
        }
        try {
            memberSignupService.create(
                    memberCreateForm.getUsername(),
                    memberCreateForm.getPassword1(),
                    memberCreateForm.getPhoneNumber()
            );
        } catch (IllegalArgumentException e) { // username이 동일할 경우 오류메시지 출력
            bindingResult.rejectValue("username", "duplicateUsername", e.getMessage());
            return "user_signup";
        }

        return "redirect:/";
    }
}

