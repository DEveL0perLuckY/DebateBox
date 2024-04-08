package com.debate.box.debate_box_backend.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OpinionDTO {

    private Integer opinionId;

    private String opinionText;

    private LocalDateTime submissionTime;

    @NotNull
    private Integer topic;

    @NotNull
    private Integer user;

}
