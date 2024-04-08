package com.debate.box.debate_box_backend.repos;

import com.debate.box.debate_box_backend.domain.Opinion;
import com.debate.box.debate_box_backend.service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
@SuppressWarnings("null")
public class OpinionListener extends AbstractMongoEventListener<Opinion> {

    private final PrimarySequenceService primarySequenceService;

    public OpinionListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Opinion> event) {
        if (event.getSource().getOpinionId() == null) {
            event.getSource().setOpinionId(((int)primarySequenceService.getNextValue()));
        }
    }

}
