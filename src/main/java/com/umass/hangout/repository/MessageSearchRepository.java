package com.umass.hangout.repository;

import com.umass.hangout.entity.Message;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MessageSearchRepository extends ElasticsearchRepository<Message, Long> {
}