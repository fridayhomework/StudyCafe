package com.studycafe.User_Signup.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.studycafe.User_Signup.Repository.MemberRepository;
import com.studycafe.User_Signup.Repository.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberSignupService {
    private final MemberRepository memberRepository;

    //비밀번호 암호화 config부분에 bean으로 등록한 거 있음
    @Autowired
    private PasswordEncoder passwordEncoder;
    //회원 가입을 위한 create
    public Member create(String username, String password, String phoneNumber) {
        //아이디 중복 체크
        if (memberRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        Member member = new Member();
        member.setUsername(username);
        member.setPassword(passwordEncoder.encode(password));
        member.setPhoneNumber(phoneNumber);
        this.memberRepository.save(member);

        return member;
    }

}
