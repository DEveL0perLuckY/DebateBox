package com.debate.box.debate_box_web.repos;

import com.debate.box.debate_box_web.domain.Role;
import com.debate.box.debate_box_web.domain.User;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Integer> {

    User findFirstByRoleId(Role role);

    List<User> findAllByRoleId(Role role);

    Optional<User> findByUserName(String userName);
    
}
