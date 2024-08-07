package com.project.petmanagement.petmanagement.services;

import com.project.petmanagement.petmanagement.JWT.JWTUserDetail;
import com.project.petmanagement.petmanagement.advices.DataNotFoundException;
import com.project.petmanagement.petmanagement.models.entity.Role;
import com.project.petmanagement.petmanagement.models.entity.User;
import com.project.petmanagement.petmanagement.payloads.requests.RegisterRequest;
import com.project.petmanagement.petmanagement.payloads.requests.UserRequest;
import com.project.petmanagement.petmanagement.repositories.RoleRepository;
import com.project.petmanagement.petmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneNumber(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new JWTUserDetail(user);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void register(RegisterRequest request) throws Exception {
        Role userRole = roleRepository.findById(1L).orElseThrow(() -> new DataNotFoundException("Can not find role with ID: " + 1L));
        User user = User.builder()
                .fullName(request.getFullName())
                .dateOfBirth(request.getDateOfBirth())
                .email(request.getEmail())
                .password(this.passwordEncoder.encode(request.getPassword()))
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .role(userRole)
                .build();
        userRepository.save(user);
    }

    public Boolean setFcm(String token) {
        User user = ((JWTUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        user.setFcmToken(token);
        return userRepository.save(user).getFcmToken().equals(token);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(UserRequest userRequest) throws DataNotFoundException {
        Long userId = ((JWTUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("Can not find user with ID: " + userId));
        existingUser.setFullName(userRequest.getFullName());
        existingUser.setDateOfBirth(userRequest.getDateOfBirth());
        existingUser.setEmail(userRequest.getEmail());
        existingUser.setAddress(userRequest.getAddress());
        existingUser.setAvatar(userRequest.getAvatar());
        return userRepository.save(existingUser);
    }

    public void deleteUser() throws DataNotFoundException {
        Long userId = ((JWTUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
        User user = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("Can not find user with ID: " + userId));
        if (user != null) {
            userRepository.deleteById(userId);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public void changePassword(String password) {
        User user = ((JWTUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
    }
}
