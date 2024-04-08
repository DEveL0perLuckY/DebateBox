package com.debate.box.debate_box_backend.loginController;

import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.debate.box.debate_box_backend.domain.Opinion;
import com.debate.box.debate_box_backend.domain.Topic;
import com.debate.box.debate_box_backend.domain.User;
import com.debate.box.debate_box_backend.model.ProfileDTO;
import com.debate.box.debate_box_backend.repos.OpinionRepository;
import com.debate.box.debate_box_backend.repos.TopicRepository;
import com.debate.box.debate_box_backend.repos.UserRepository;

import lombok.Getter;
import lombok.Setter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RequestMapping(value = "/api/", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
@RestController
public class DemoController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/profile")
    public ResponseEntity<ProfileDTO> profile(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().build();
        }
        String token = authHeader.substring(7);
        Optional<User> user = userRepository.findByToken(token);

        return user.map(
                u -> ResponseEntity.ok(new ProfileDTO(u.getUserId(), u.getUsername(), u.getEmail(), u.getRoleId())))
                .orElse(ResponseEntity.notFound().build());
    }

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    OpinionRepository opinionRepository;

    @GetMapping("/topic/{id}")
    public ResponseEntity<MyTopics> showTopicDetails(@PathVariable("id") Integer topicId) {

        Optional<Topic> topicOptional = topicRepository.findById(topicId);

        if (topicOptional.isPresent()) {
            Topic topic = topicOptional.get();
            MyTopics myTopics = new MyTopics();
            myTopics.setId(topic.getTopicId());
            myTopics.setTopicDescription(topic.getTopicDescription());
            myTopics.setTopicName(topic.getTopicName());
            List<OpinionList> opinionLists = new ArrayList<>();
            for (Opinion opinion : topic.getTopicOpinions()) {
                OpinionList opinionList = new OpinionList();
                opinionList.setOpinionId(opinion.getOpinionId());
                opinionList.setOpinionText(opinion.getOpinionText());
                opinionList.setSubmissionTime(opinion.getSubmissionTime());
                opinionList.setUserName(opinion.getUser().getUsername());
                opinionLists.add(opinionList);
            }
            myTopics.setList(opinionLists);
            return ResponseEntity.ok(myTopics);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/submitOpinion")
    public ResponseEntity<?> submitOpinion(@RequestBody OpinionDTO2 opinionDTO,
            @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().build();
        }
        String token = authHeader.substring(7);
        Optional<User> userOptional = userRepository.findByToken(token);

        return userOptional.map(user -> {
            Opinion opinion = new Opinion();
            opinion.setOpinionText(opinionDTO.getOpinionText());
            opinion.setSubmissionTime(LocalDateTime.now());
            Topic topic = topicRepository.findById(opinionDTO.getId()).orElse(null);
            if (topic != null) {
                opinion.setTopic(topic);
                opinion.setUser(user);
                opinionRepository.save(opinion);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        }).orElse(ResponseEntity.badRequest().build());
    }

}

@Getter
@Setter
class OpinionDTO2 {

    private String opinionText;
    private Integer id;

}

@Getter
@Setter
class MyTopics {

    Integer id;
    private String topicName;
    private String topicDescription;
    List<OpinionList> list;
}

@Getter
@Setter
class OpinionList {
    private Integer opinionId;
    private String opinionText;
    private LocalDateTime submissionTime;
    private String userName;
}