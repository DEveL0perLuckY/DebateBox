package com.debate.box.debate_box_backend.service;

import com.debate.box.debate_box_backend.domain.Opinion;
import com.debate.box.debate_box_backend.domain.Topic;
import com.debate.box.debate_box_backend.model.TopicDTO;
import com.debate.box.debate_box_backend.repos.OpinionRepository;
import com.debate.box.debate_box_backend.repos.TopicRepository;
import com.debate.box.debate_box_backend.util.NotFoundException;
import com.debate.box.debate_box_backend.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@SuppressWarnings("null")

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final OpinionRepository opinionRepository;

    public TopicService(final TopicRepository topicRepository,
            final OpinionRepository opinionRepository) {
        this.topicRepository = topicRepository;
        this.opinionRepository = opinionRepository;
    }

    public List<TopicDTO> findAll() {
        final List<Topic> topics = topicRepository.findAll(Sort.by("topicId"));
        return topics.stream()
                .map(topic -> mapToDTO(topic, new TopicDTO()))
                .toList();
    }

    public TopicDTO get(final Integer topicId) {
        return topicRepository.findById(topicId)
                .map(topic -> mapToDTO(topic, new TopicDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final TopicDTO topicDTO) {
        final Topic topic = new Topic();
        mapToEntity(topicDTO, topic);
        return topicRepository.save(topic).getTopicId();
    }

    public void update(final Integer topicId, final TopicDTO topicDTO) {
        final Topic topic = topicRepository.findById(topicId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(topicDTO, topic);
        topicRepository.save(topic);
    }

    public void delete(final Integer topicId) {
        topicRepository.deleteById(topicId);
    }

    private TopicDTO mapToDTO(final Topic topic, final TopicDTO topicDTO) {
        topicDTO.setTopicId(topic.getTopicId());
        topicDTO.setTopicName(topic.getTopicName());
        topicDTO.setTopicDescription(topic.getTopicDescription());
        return topicDTO;
    }

    private Topic mapToEntity(final TopicDTO topicDTO, final Topic topic) {
        topic.setTopicName(topicDTO.getTopicName());
        topic.setTopicDescription(topicDTO.getTopicDescription());
        return topic;
    }

    public ReferencedWarning getReferencedWarning(final Integer topicId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Topic topic = topicRepository.findById(topicId)
                .orElseThrow(NotFoundException::new);
        final Opinion topicOpinion = opinionRepository.findFirstByTopic(topic);
        if (topicOpinion != null) {
            referencedWarning.setKey("topic.opinion.topic.referenced");
            referencedWarning.addParam(topicOpinion.getOpinionId());
            return referencedWarning;
        }
        return null;
    }

}
