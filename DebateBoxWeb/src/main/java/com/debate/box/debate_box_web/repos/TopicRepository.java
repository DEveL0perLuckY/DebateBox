package com.debate.box.debate_box_web.repos;

import com.debate.box.debate_box_web.domain.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface TopicRepository extends MongoRepository<Topic, Integer> {
}
