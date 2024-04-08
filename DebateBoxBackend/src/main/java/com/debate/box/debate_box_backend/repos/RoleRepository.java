package com.debate.box.debate_box_backend.repos;

import com.debate.box.debate_box_backend.domain.Role;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface RoleRepository extends MongoRepository<Role, Long> {
}
