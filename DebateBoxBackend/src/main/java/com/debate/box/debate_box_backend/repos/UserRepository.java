package com.debate.box.debate_box_backend.repos;

import com.debate.box.debate_box_backend.domain.Role;
import com.debate.box.debate_box_backend.domain.User;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Integer> {

    User findFirstByRoleId(Role role);

    List<User> findAllByRoleId(Role role);

    Optional<User> findByEmail(String email);

    Optional<User> findByUserName(String userName);

    Optional<User> findByToken(String token);
}
