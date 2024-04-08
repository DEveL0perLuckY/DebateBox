package com.debate.box.debate_box_backend.rest;

import com.debate.box.debate_box_backend.model.TopicDTO;
import com.debate.box.debate_box_backend.service.TopicService;
import com.debate.box.debate_box_backend.util.ReferencedException;
import com.debate.box.debate_box_backend.util.ReferencedWarning;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/topics", produces = MediaType.APPLICATION_JSON_VALUE)
public class TopicResource {

    private final TopicService topicService;

    public TopicResource(final TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<List<TopicDTO>> getAllTopics() {
        return ResponseEntity.ok(topicService.findAll());
    }

    @GetMapping("/{topicId}")
    public ResponseEntity<TopicDTO> getTopic(
            @PathVariable(name = "topicId") final Integer topicId) {
        return ResponseEntity.ok(topicService.get(topicId));
    }

    @PostMapping
    public ResponseEntity<Integer> createTopic(@RequestBody @Valid final TopicDTO topicDTO) {
        final Integer createdTopicId = topicService.create(topicDTO);
        return new ResponseEntity<>(createdTopicId, HttpStatus.CREATED);
    }

    @PutMapping("/{topicId}")
    public ResponseEntity<Integer> updateTopic(
            @PathVariable(name = "topicId") final Integer topicId,
            @RequestBody @Valid final TopicDTO topicDTO) {
        topicService.update(topicId, topicDTO);
        return ResponseEntity.ok(topicId);
    }

    @DeleteMapping("/{topicId}")
    public ResponseEntity<Void> deleteTopic(@PathVariable(name = "topicId") final Integer topicId) {
        final ReferencedWarning referencedWarning = topicService.getReferencedWarning(topicId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        topicService.delete(topicId);
        return ResponseEntity.noContent().build();
    }

}
