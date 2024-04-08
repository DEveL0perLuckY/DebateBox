package com.debate.box.debate_box_backend.repos;

import com.debate.box.debate_box_backend.domain.Topic;
import com.debate.box.debate_box_backend.service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@SuppressWarnings("null")
@Component
public class TopicListener extends AbstractMongoEventListener<Topic> {

    private final PrimarySequenceService primarySequenceService;

    public TopicListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Topic> event) {
        if (event.getSource().getTopicId() == null) {
            event.getSource().setTopicId(((int)primarySequenceService.getNextValue()));
        }
    }

}
