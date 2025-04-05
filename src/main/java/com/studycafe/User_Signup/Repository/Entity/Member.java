package com.studycafe.User_Signup.Repository.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity // 데이터베이스에서의 하나의 객체 예) 학생
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String password;

    @Column(name = "username")
    private String username;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

}
