package com.assignment.service;

import com.assignment.entity.UserEntity;
import com.assignment.repository.UserRepository;
import com.assignment.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<UserEntity> userOpt = userRepository.findByEmail(email);

        if(userOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email : "+email);
        }
        UserEntity user = userOpt.get();

        return new UserDetailsImpl(user);
    }
}
