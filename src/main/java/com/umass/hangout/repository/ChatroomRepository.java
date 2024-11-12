package com.umass.hangout.repository;

import com.umass.hangout.entity.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {
    Optional<Chatroom> findByName(String name);
}
