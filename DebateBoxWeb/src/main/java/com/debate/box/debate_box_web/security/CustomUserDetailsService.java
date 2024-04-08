package com.debate.box.debate_box_web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.debate.box.debate_box_web.domain.User;
import com.debate.box.debate_box_web.repos.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(email);
        if (user.isPresent()) {
            System.out.println("true");
            return buildUserDetails(user.get());
        } else {
            System.out.println("false");
            throw new UsernameNotFoundException("Invalid email or password login failed");
        }
    }

    private UserDetails buildUserDetails(User user) {
        List<GrantedAuthority> authorities = user.getRoleId().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                authorities);
    }

}
