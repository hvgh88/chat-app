package com.umass.hangout.repository.jpa;

import com.umass.hangout.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("groupJpaRepository")
public interface GroupRepository extends JpaRepository<Group, Long> {
}