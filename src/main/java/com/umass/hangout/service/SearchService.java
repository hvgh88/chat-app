package com.umass.hangout.service;

import com.umass.hangout.entity.Group;
import com.umass.hangout.entity.Message;
import com.umass.hangout.repository.GroupSearchRepository;
import com.umass.hangout.repository.MessageSearchRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    @Autowired
    private GroupSearchRepository groupSearchRepository;

    @Autowired
    private MessageSearchRepository messageSearchRepository;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    public List<Group> searchGroups(String keyword) {
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyword, "name", "location"))
                .build();

        SearchHits<Group> searchHits = elasticsearchOperations.search(searchQuery, Group.class);
        return searchHits.getSearchHits().stream()
                .map(searchHit -> searchHit.getContent())
                .collect(Collectors.toList());
    }

    public List<Message> searchMessagesInGroup(Long groupId, String keyword) {
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("group.id", groupId))
                        .must(QueryBuilders.matchQuery("content", keyword)))
                .build();

        SearchHits<Message> searchHits = elasticsearchOperations.search(searchQuery, Message.class);
        return searchHits.getSearchHits().stream()
                .map(searchHit -> searchHit.getContent())
                .collect(Collectors.toList());
    }
}