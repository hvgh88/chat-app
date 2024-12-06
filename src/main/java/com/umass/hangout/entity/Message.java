package com.umass.hangout.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Document(indexName = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User sender;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

}