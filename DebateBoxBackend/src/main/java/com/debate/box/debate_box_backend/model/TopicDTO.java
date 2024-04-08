package com.debate.box.debate_box_backend.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TopicDTO {

    private Integer topicId;

    @NotNull
    @Size(max = 100)
    private String topicName;

    private String topicDescription;

}
