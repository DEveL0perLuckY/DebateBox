package com.debate.box.debate_box_web.loginController;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.debate.box.debate_box_web.domain.Opinion;
import com.debate.box.debate_box_web.domain.Topic;
import com.debate.box.debate_box_web.domain.User;
import com.debate.box.debate_box_web.repos.OpinionRepository;
import com.debate.box.debate_box_web.repos.TopicRepository;
import com.debate.box.debate_box_web.repos.UserRepository;

@Controller
@SuppressWarnings("null")
public class Mycontroller {

    @Autowired
    UserRepository userRepository;
    @Autowired
    OpinionRepository opinionRepository;

    @Autowired
    TopicRepository topicRepository;

    @GetMapping("/topic/{topicId}")
    public String showTopicDetails(@PathVariable("topicId") Integer topicId, Model model) {
        Topic topic = topicRepository.findById(topicId).get();
        model.addAttribute("id", topicId);
        model.addAttribute("topicDescription", topic.getTopicDescription());
        model.addAttribute("topicOpinions", topic.getTopicOpinions());
        return "home/topic";
    }

    @PostMapping("/submitOpinion")
    public String submitOpinion(@RequestParam("opinionText") String opinionText, @RequestParam("id") Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUserName(username);
        User user = userOptional.get();
        Opinion opinion = new Opinion();
        opinion.setOpinionText(opinionText);
        opinion.setSubmissionTime(LocalDateTime.now());
        Topic topic = topicRepository.findById(id).get();
        opinion.setTopic(topic);
        opinion.setUser(user);
        opinionRepository.save(opinion);
        return "redirect:/topic/" + id;
    }
}
