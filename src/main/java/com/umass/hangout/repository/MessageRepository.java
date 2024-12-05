package com.umass.hangout.repository;

import com.umass.hangout.entity.Message;
import com.umass.hangout.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByGroupOrderByTimestampAsc(Group group);
}