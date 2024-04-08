package com.debate.box.debate_box_backend.domain;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document
@Getter
@Setter
public class Opinion {

    @Id
    private Integer opinionId;

    private String opinionText;

    private LocalDateTime submissionTime;

    @DocumentReference(lazy = true)
    @NotNull
    private Topic topic;

    @DocumentReference(lazy = true)
    @NotNull
    private User user;

}
