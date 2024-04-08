package com.debate.box.debate_box_backend.repos;

import com.debate.box.debate_box_backend.domain.Opinion;
import com.debate.box.debate_box_backend.domain.Topic;
import com.debate.box.debate_box_backend.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface OpinionRepository extends MongoRepository<Opinion, Integer> {

    Opinion findFirstByTopic(Topic topic);

    Opinion findFirstByUser(User user);

}
