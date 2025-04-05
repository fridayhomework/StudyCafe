package com.studycafe.Seatmanagement.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.studycafe.Seatmanagement.DTO.SeatDTO;
import com.studycafe.Seatmanagement.Service.SeatService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/seat")
public class SeatController {
    private final SeatService seatService;

    @GetMapping("/list")
    public String showSeatList(Model model) {
        List<SeatDTO> seats = seatService.getAllSeatsAsDTO();
        model.addAttribute("seats", seats);
        return "admin_seat_list";
    }
    // API로도 내려줄 수 있게 해줌 (React용)
    @GetMapping("/api/list")
    @ResponseBody
    public List<SeatDTO> getSeatsAsAPI() {
        return seatService.getAllSeatsAsDTO();
    }

    // 관리자가 대기 걸기
    @PostMapping("/set-waiting/{seatId}")
    public String setSeatWaiting(@PathVariable int seatId) {
        seatService.setSeatWaiting(seatId);
        return "redirect:/admin/seat/list";
    }

    // 학생이 선택
    @PostMapping("/student/select/{seatId}")
    public String studentSelect(@PathVariable int seatId, Principal principal) {
        seatService.studentSelectSeat(seatId, principal.getName());
        return "redirect:/admin/seat/list";
    }
}
