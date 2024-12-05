package com.umass.hangout.repository;

import com.umass.hangout.entity.Group;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GroupSearchRepository extends ElasticsearchRepository<Group, Long> {
}