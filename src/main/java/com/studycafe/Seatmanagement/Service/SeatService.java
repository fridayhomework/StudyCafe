package com.studycafe.Seatmanagement.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.studycafe.Seatmanagement.DTO.SeatDTO;
import com.studycafe.Seatmanagement.Entity.Seat;
import com.studycafe.Seatmanagement.Repository.SeatRepository;
import com.studycafe.User_Signup.Repository.MemberRepository;
import com.studycafe.User_Signup.Repository.Entity.Member;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // final이나 @NotNull이 붙은 필드의 생성자를 자동으로 생성해서 @Autowired로 의존성 주입해줄 필요 없음
public class SeatService {
    private final SeatRepository seatRepository;
    private final MemberRepository memberRepository;

    // 전체 좌석 DTO 변환해서 넘기기 (API or View 용)
    public List<SeatDTO> getAllSeatsAsDTO() {
        return seatRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private SeatDTO convertToDTO(Seat seat) {
        String status;
        if(seat.isSeatStatus() && !seat.isWaiting()) {
            status = "사용중";
        }
        else if(!seat.isSeatStatus() && seat.isWaiting()) {
            status = "대기중";
        }
        else{
            status = "사용불가";
        }

        return new SeatDTO(seat.getSeatNo(), seat.getSeatName(), status);
    }

    @Transactional
    public void setSeatWaiting(int seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new IllegalArgumentException("좌석 없음"));
        if (seat.isSeatStatus()) {
            throw new IllegalStateException("이미 사용 중");
        }
        seat.setWaiting();
    }

    @Transactional
    public void studentSelectSeat(int seatId, String username) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new IllegalArgumentException("좌석 없음"));
        if (!seat.isWaiting()) {
            throw new IllegalStateException("좌석 선택 불가능");
        }

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        seat.assignTo(member);
    }

}
