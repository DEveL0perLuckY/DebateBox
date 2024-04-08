package com.debate.box.debate_box_web.loginController;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.debate.box.debate_box_web.domain.Role;
import com.debate.box.debate_box_web.domain.User;
import com.debate.box.debate_box_web.model.UserDTO;
import com.debate.box.debate_box_web.repos.RoleRepository;
import com.debate.box.debate_box_web.repos.UserRepository;
import com.debate.box.debate_box_web.util.WebUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@SuppressWarnings("null")
@Controller
public class RegistrationController {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/unAuthorized")
    public String unAuthorized() {
        return "Pages/unAuthorized";
    }

    @GetMapping("/signup")
    public String register(Model model) {
        model.addAttribute("obj", new UserDTO());
        return "Pages/SingUp";
    }

    @PostMapping("/signup")
    public String registration(@Valid @ModelAttribute("obj") UserDTO userDto,
            final RedirectAttributes redirectAttributes, BindingResult result, Model model) {
        if (userAlreadyRegistered(userDto.getUserName(), result)) {
            return "Pages/SingUp";
        }
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "Pages/SingUp";
        }

        Optional<Role> role = roleRepository.findById(Constant.ROLE_USER);

        Set<Role> roles = new HashSet<>();
        if (role.isPresent()) {
            roles.add(role.get());
        } else {

            Role ROLE_ADMIN = new Role();
            ROLE_ADMIN.setId(Constant.ROLE_ADMIN);
            ROLE_ADMIN.setName("ROLE_ADMIN");

            Role ROLE_USER = new Role();
            ROLE_USER.setId(Constant.ROLE_USER);
            ROLE_USER.setName("ROLE_USER");

            roleRepository.saveAll(Arrays.asList(ROLE_ADMIN, ROLE_USER));
            roles.addAll(Arrays.asList(ROLE_ADMIN, ROLE_USER));
        }
        User user = new User();
        try {
            user.setUserName(userDto.getUserName());
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setRoleId(roles);
            userRepository.save(user);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS,
                    WebUtils.getMessage("Registration successfully!"));
            return "redirect:/login";
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage("Registration failed. Please try again."));
            return "redirect:/login?fail";
        }

    }

    private boolean userAlreadyRegistered(String email, BindingResult result) {
        Optional<User> existingUser = userRepository.findByUserName(email);

        if (existingUser.isPresent()) {
            result.rejectValue("email", null, "User already registered!");
            return true;
        }
        return false;
    }
}
