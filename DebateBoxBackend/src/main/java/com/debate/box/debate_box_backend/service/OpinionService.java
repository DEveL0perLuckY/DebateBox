package com.debate.box.debate_box_backend.service;

import com.debate.box.debate_box_backend.domain.Opinion;
import com.debate.box.debate_box_backend.domain.Topic;
import com.debate.box.debate_box_backend.domain.User;
import com.debate.box.debate_box_backend.model.OpinionDTO;
import com.debate.box.debate_box_backend.repos.OpinionRepository;
import com.debate.box.debate_box_backend.repos.TopicRepository;
import com.debate.box.debate_box_backend.repos.UserRepository;
import com.debate.box.debate_box_backend.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@SuppressWarnings("null")

@Service
public class OpinionService {

    private final OpinionRepository opinionRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public OpinionService(final OpinionRepository opinionRepository,
            final TopicRepository topicRepository, final UserRepository userRepository) {
        this.opinionRepository = opinionRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    public List<OpinionDTO> findAll() {
        final List<Opinion> opinions = opinionRepository.findAll(Sort.by("opinionId"));
        return opinions.stream()
                .map(opinion -> mapToDTO(opinion, new OpinionDTO()))
                .toList();
    }

    public OpinionDTO get(final Integer opinionId) {
        return opinionRepository.findById(opinionId)
                .map(opinion -> mapToDTO(opinion, new OpinionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final OpinionDTO opinionDTO) {
        final Opinion opinion = new Opinion();
        mapToEntity(opinionDTO, opinion);
        return opinionRepository.save(opinion).getOpinionId();
    }

    public void update(final Integer opinionId, final OpinionDTO opinionDTO) {
        final Opinion opinion = opinionRepository.findById(opinionId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(opinionDTO, opinion);
        opinionRepository.save(opinion);
    }

    public void delete(final Integer opinionId) {
        opinionRepository.deleteById(opinionId);
    }

    private OpinionDTO mapToDTO(final Opinion opinion, final OpinionDTO opinionDTO) {
        opinionDTO.setOpinionId(opinion.getOpinionId());
        opinionDTO.setOpinionText(opinion.getOpinionText());
        opinionDTO.setSubmissionTime(opinion.getSubmissionTime());
        opinionDTO.setTopic(opinion.getTopic() == null ? null : opinion.getTopic().getTopicId());
        opinionDTO.setUser(opinion.getUser() == null ? null : opinion.getUser().getUserId());
        return opinionDTO;
    }

    private Opinion mapToEntity(final OpinionDTO opinionDTO, final Opinion opinion) {
        opinion.setOpinionText(opinionDTO.getOpinionText());
        opinion.setSubmissionTime(opinionDTO.getSubmissionTime());
        final Topic topic = opinionDTO.getTopic() == null ? null : topicRepository.findById(opinionDTO.getTopic())
                .orElseThrow(() -> new NotFoundException("topic not found"));
        opinion.setTopic(topic);
        final User user = opinionDTO.getUser() == null ? null : userRepository.findById(opinionDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        opinion.setUser(user);
        return opinion;
    }

}
