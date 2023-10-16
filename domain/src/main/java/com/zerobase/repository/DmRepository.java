package com.zerobase.repository;

import com.zerobase.model.dm.dm.domain.model.Dm;
import com.zerobase.model.dm.room.domain.model.DmRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DmRepository extends JpaRepository<Dm, Long> {
    List<Dm> findByDmroom(DmRoom dmRoom);
}
