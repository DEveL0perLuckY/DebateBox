package com.debate.box.debate_box_web.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RoleDTO {

    private Long id;

    @Size(max = 25)
    private String name;

}
