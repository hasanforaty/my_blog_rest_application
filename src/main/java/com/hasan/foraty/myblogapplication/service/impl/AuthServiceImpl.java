package com.hasan.foraty.myblogapplication.service.impl;

import com.hasan.foraty.myblogapplication.entity.Roles;
import com.hasan.foraty.myblogapplication.entity.Users;
import com.hasan.foraty.myblogapplication.exception.BlogApiException;
import com.hasan.foraty.myblogapplication.payload.LoginDto;
import com.hasan.foraty.myblogapplication.payload.SignUpDto;
import com.hasan.foraty.myblogapplication.repository.RoleRepository;
import com.hasan.foraty.myblogapplication.repository.UserRepository;
import com.hasan.foraty.myblogapplication.service.AuthService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUserNameOrEmail(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "User Logged-in successfully";
    }

    @Override
    @Transactional
    public String register(SignUpDto signUpDto) {
        //add check for username exists in database
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            throw new BlogApiException("Username is already exists", HttpStatus.BAD_REQUEST);
        }
        //check for email exist in database
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            throw new BlogApiException("Email is already exists", HttpStatus.BAD_REQUEST);
        }
        Users users = modelMapper.map(signUpDto,Users.class);
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Roles role = roleRepository.findByName("ROLE_USER").get();
        users.addRole(role);

        userRepository.save(users);
        return "User registered successfully";
    }
}
