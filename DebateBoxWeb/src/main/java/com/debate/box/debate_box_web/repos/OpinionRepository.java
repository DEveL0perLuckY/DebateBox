package com.debate.box.debate_box_web.repos;

import com.debate.box.debate_box_web.domain.Opinion;
import com.debate.box.debate_box_web.domain.Topic;
import com.debate.box.debate_box_web.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface OpinionRepository extends MongoRepository<Opinion, Integer> {

    Opinion findFirstByTopic(Topic topic);

    Opinion findFirstByUser(User user);

}
