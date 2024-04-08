package com.debate.box.debate_box_backend.loginController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.debate.box.debate_box_backend.domain.User;
import com.debate.box.debate_box_backend.model.LoginReq;
import com.debate.box.debate_box_backend.myservice.AuthenticationResponse;
import com.debate.box.debate_box_backend.myservice.AuthenticationService;
import com.debate.box.debate_box_backend.repos.UserRepository;

@RequestMapping(value = "/api/", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
@RestController
public class AuthenticationController {

    @Autowired
    AuthenticationService authService;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginReq request) {

        return ResponseEntity.ok(authService.authenticate(request));
    }
}
