package com.debate.box.debate_box_backend.rest;

import com.debate.box.debate_box_backend.model.OpinionDTO;
import com.debate.box.debate_box_backend.service.OpinionService;
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
@RequestMapping(value = "/api/opinions", produces = MediaType.APPLICATION_JSON_VALUE)
public class OpinionResource {

    private final OpinionService opinionService;

    public OpinionResource(final OpinionService opinionService) {
        this.opinionService = opinionService;
    }

    @GetMapping
    public ResponseEntity<List<OpinionDTO>> getAllOpinions() {
        return ResponseEntity.ok(opinionService.findAll());
    }

    @GetMapping("/{opinionId}")
    public ResponseEntity<OpinionDTO> getOpinion(
            @PathVariable(name = "opinionId") final Integer opinionId) {
        return ResponseEntity.ok(opinionService.get(opinionId));
    }

    @PostMapping
    public ResponseEntity<Integer> createOpinion(@RequestBody @Valid final OpinionDTO opinionDTO) {
        final Integer createdOpinionId = opinionService.create(opinionDTO);
        return new ResponseEntity<>(createdOpinionId, HttpStatus.CREATED);
    }

    @PutMapping("/{opinionId}")
    public ResponseEntity<Integer> updateOpinion(
            @PathVariable(name = "opinionId") final Integer opinionId,
            @RequestBody @Valid final OpinionDTO opinionDTO) {
        opinionService.update(opinionId, opinionDTO);
        return ResponseEntity.ok(opinionId);
    }

    @DeleteMapping("/{opinionId}")
    public ResponseEntity<Void> deleteOpinion(
            @PathVariable(name = "opinionId") final Integer opinionId) {
        opinionService.delete(opinionId);
        return ResponseEntity.noContent().build();
    }

}
