package com.assignment.service;

import com.assignment.dto.*;
import com.assignment.entity.UserEntity;
import com.assignment.exception.UserNotFoundException;
import com.assignment.repository.UserRepository;
import com.assignment.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    //Register new user
    @Transactional
    public void registerUser(UserRegisterDto registerDto, String encodedPassword){
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new RuntimeException("User with this email already exists!");
        }

        UserEntity user = new UserEntity();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }

    public Optional<String> authenticateUser(UserLoginDto loginDto){
        Optional<UserEntity> userOpt = userRepository.findByEmail(loginDto.getEmail());

        if(userOpt.isPresent()){
            UserEntity user = userOpt.get();

            if(passwordEncoder.matches(loginDto.getPassword(),user.getPassword())){
                String token = jwtUtil.generateToken(user.getEmail());
                return Optional.of(token);
            }
        }
        return Optional.empty();
    }

    // Check existing email
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    // Get User by Email
    public UserDto getUserByEmail(String email){
        Optional<UserEntity> userOpt = userRepository.findByEmail(email);

        if(userOpt.isPresent()){
            UserEntity user = userOpt.get();
            return new UserDto(user.getId(), user.getName(), user.getEmail());
        }else{
            throw new UserNotFoundException("User not found with email : "+ email);
        }
    }

    // Update existing user
    @Transactional
    public UserDto updateUser(String email, UserUpdateDto updateDto){
        Optional<UserEntity> userOpt = userRepository.findByEmail(email);

        if(userOpt.isPresent()){
            UserEntity user = userOpt.get();

            user.setName(updateDto.getName());
            user.setEmail(updateDto.getEmail());

            userRepository.save(user);
            return new UserDto(user.getId(), user.getName(), user.getEmail());
        }
        return null;
    }

    //Delete user
    @Transactional
    public void deleteUser(String email){
        Optional<UserEntity> userOpt = userRepository.findByEmail(email);
        if(userOpt.isPresent()){
            userRepository.delete(userOpt.get());
        }else{
            throw new UserNotFoundException("Cannot delete. User not found with email : "+email);
        }

    }


    @Transactional
    public void updatePassword(String email, PasswordUpdateDto passwordUpdateDto){
        Optional<UserEntity> userOpt = userRepository.findByEmail(email);

        if(userOpt.isEmpty()){
            throw new UserNotFoundException("User not found with email : "+email);
        }
        UserEntity user = userOpt.get();

        if(!passwordEncoder.matches(passwordUpdateDto.getOldPassword(), user.getPassword())){
            throw new RuntimeException("Old password is incorrect!");
        }
        if(!passwordUpdateDto.getNewPassword().equals(passwordUpdateDto.getConfirmPassword())){
            throw new RuntimeException("New password and confirm password do not match!");
        }
//        if(!isValidPassword(passwordUpdateDto.getNewPassword())){
//            throw new RuntimeException("Password must be at least 8 character with uppercase, lowercase and special character.");
//        }

        if(passwordEncoder.matches(passwordUpdateDto.getNewPassword(), user.getPassword())){
            throw new RuntimeException("New password cannot be the same as the current password!");
        }

        String encodedNewPassword = passwordEncoder.encode(passwordUpdateDto.getNewPassword());
        user.setPassword(encodedNewPassword);
        userRepository.save(user);

        System.out.println("User with email "+ email+" changed their password successfully.");

        invalidateUserTokens(email);
    }

//    private boolean isValidPassword(String password){
//        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$");
//    }

    private void invalidateUserTokens(String email){
        System.out.println("Invalidating old JWT tokens for user : "+ email);
    }

//    public Optional<UserEntity> findByEmail(String email){
//        return userRepository.findByEmail(email);
//    }

//    public void saveUser(UserEntity user){
//        userRepository.save(user);
//    }


}
