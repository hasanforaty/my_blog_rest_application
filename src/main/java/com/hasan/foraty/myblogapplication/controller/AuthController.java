package com.hasan.foraty.myblogapplication.controller;

import com.hasan.foraty.myblogapplication.payload.JWTAuthResponse;
import com.hasan.foraty.myblogapplication.payload.LoginDto;
import com.hasan.foraty.myblogapplication.payload.SignUpDto;
import com.hasan.foraty.myblogapplication.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = {"/login","/sign_in"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
        String  token = authService.login(loginDto);
        var response = new JWTAuthResponse();
        response.setAccessToken(token);
        return ResponseEntity.ok(response);
    }
    @PostMapping({"/register","sing_up"})
    public ResponseEntity<String > register(@RequestBody SignUpDto signUpDto){

        return new ResponseEntity<>(authService.register(signUpDto), HttpStatus.CREATED);
    }
}
