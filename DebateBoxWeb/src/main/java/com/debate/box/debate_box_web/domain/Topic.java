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
public class Topic {

    @Id
    private Integer topicId;

    @NotNull
    @Size(max = 100)
    private String topicName;

    private String topicDescription;

    @DocumentReference(lazy = true, lookup = "{ 'topic' : ?#{#self._id} }")
    @ReadOnlyProperty
    private Set<Opinion> topicOpinions;

}
