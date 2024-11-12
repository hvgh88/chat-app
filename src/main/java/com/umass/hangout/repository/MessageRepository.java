package com.umass.hangout.repository;

import com.umass.hangout.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatroomId(Long chatroomId);
}
