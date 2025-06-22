package com.medilabo.front.services;

import com.medilabo.front.entity.User;
import com.medilabo.front.repository.UserRepository;
import com.medilabo.front.security.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
@Service
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        log.info("Try to found user : " + username);

        if(user == null) {
            throw new UsernameNotFoundException("User not found with following username/email : '" + username + "'");
        }

        return new CustomUserDetails(user.getId(), userRepository);
    }
}
