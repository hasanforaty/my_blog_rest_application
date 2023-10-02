package com.hasan.foraty.myblogapplication.service;

import com.hasan.foraty.myblogapplication.payload.LoginDto;
import com.hasan.foraty.myblogapplication.payload.SignUpDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(SignUpDto signUpDto);
}
