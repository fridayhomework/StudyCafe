package com.studycafe.Seatmanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.studycafe.Seatmanagement.Entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, Integer> {
}
