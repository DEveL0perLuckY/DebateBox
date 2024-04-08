package com.debate.box.debate_box_web.repos;

import com.debate.box.debate_box_web.domain.Role;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface RoleRepository extends MongoRepository<Role, Long> {
}
