package com.studycafe.Seatmanagement.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.studycafe.User_Signup.Repository.Entity.Member;

@Entity
@Getter
@NoArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seatNo; // 좌석 번호

    private String seatName; // 좌석 이름
    private boolean seatStatus; // 좌석 사용 여부
    private boolean isWaiting; // 관리자가 허용한 좌석, true -> 관리자가 골라도 된다고 허용한 상태
    // seatStatus = false isWaiting = true -> 좌석 선택 가능
    // seatStatus = false isWaiting = false -> 앉지 못하는 좌석
    // seatStatus = true isWaiting = false -> 학생이 선택을 했기 때문에 선택 불가

    @ManyToOne // 여러 좌석이 하나의 학생에게 속할 수 있음
    @JoinColumn(name = "member_id") // 외래키 생성
    private Member member;

    public Seat(String seatName){
        this.seatName = seatName;
        this.seatStatus = false;
        this.isWaiting = false;
    }

    // 관리자: 대기 걸기
    public void setWaiting() {
        this.isWaiting = true;
    }

    // 관리자: 대기 취소
    public void cancelWaiting() {
        this.isWaiting = false;
    }

    // 학생 좌석 선택 처리
    public void assignTo(Member member) {
        this.member = member;
        this.seatStatus = true;
        this.isWaiting = false;
    }
}
