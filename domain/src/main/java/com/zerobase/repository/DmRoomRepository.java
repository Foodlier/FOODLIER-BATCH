package com.zerobase.repository;

import com.zerobase.model.dm.room.domain.model.DmRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DmRoomRepository extends JpaRepository<DmRoom, Long> {
}
