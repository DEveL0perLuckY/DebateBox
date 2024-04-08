package com.debate.box.debate_box_web.repos;

import com.debate.box.debate_box_web.domain.Role;
import com.debate.box.debate_box_web.service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@SuppressWarnings("null")

@Component
public class RoleListener extends AbstractMongoEventListener<Role> {

    private final PrimarySequenceService primarySequenceService;

    public RoleListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Role> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(primarySequenceService.getNextValue());
        }
    }

}
