package com.debate.box.debate_box_backend.repos;

import com.debate.box.debate_box_backend.domain.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface TopicRepository extends MongoRepository<Topic, Integer> {
}
