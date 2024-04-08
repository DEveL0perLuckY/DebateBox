package com.debate.box.debate_box_web.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Document
@Getter
@Setter
public class User {

    @Id
    private Integer userId;

    @NotNull
    @Size(max = 50)
    private String userName;

    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String password;

    @DocumentReference(lazy = true, lookup = "{ 'user' : ?#{#self._id} }")
    @ReadOnlyProperty
    private Set<Opinion> userOpinions;

    @DocumentReference(lazy = true)
    private Set<Role> roleId;

}
