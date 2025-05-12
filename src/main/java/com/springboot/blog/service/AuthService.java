package com.springboot.blog.service;

import com.springboot.blog.payload.LoginDTO;
import com.springboot.blog.payload.RegisterDTO;

public interface AuthService {
    // login/signin
    String login(LoginDTO loginDTO);
    // register/signup
    String register(RegisterDTO registerDTO);
}
