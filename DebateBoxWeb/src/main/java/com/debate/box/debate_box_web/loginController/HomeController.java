package com.debate.box.debate_box_web.loginController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.debate.box.debate_box_web.repos.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String index() {
        return "home/index";
    }

    @GetMapping("/user")
    public String indexuser() {
        return "home/USER";
    }

    @GetMapping("/meeting")
    public String admi2n() {
        return "home/meeting";
    }

}
