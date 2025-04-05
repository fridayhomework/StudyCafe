package com.studycafe.User_Signup.Repository;
import com.studycafe.User_Signup.Repository.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    //username이 존재하는지 확인
    boolean existsByUsername(String username);

    //username 조회하기
    Optional<Member> findByUsername(String username);
}
