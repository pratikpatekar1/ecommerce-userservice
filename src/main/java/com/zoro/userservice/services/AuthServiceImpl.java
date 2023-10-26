package com.zoro.userservice.services;

import com.zoro.userservice.dtos.LoginRequestDto;
import com.zoro.userservice.dtos.SignUpRequestDto;
import com.zoro.userservice.dtos.UserDto;
import com.zoro.userservice.exceptions.BadCredentialsException;
import com.zoro.userservice.exceptions.NotFoundException;
import com.zoro.userservice.models.Session;
import com.zoro.userservice.models.SessionStatus;
import com.zoro.userservice.models.User;
import com.zoro.userservice.repositories.SessionRepository;
import com.zoro.userservice.repositories.UserRepository;
import com.zoro.userservice.security.JwtUtil;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import java.util.*;

@Primary
@Service
public class AuthServiceImpl implements AuthService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private JwtUtil jwtUtil;
    public AuthServiceImpl(UserRepository userRepository, SessionRepository sessionRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserDto signup(SignUpRequestDto signUpRequestDto) {
        User user = new User();
        user.setEmail(signUpRequestDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(signUpRequestDto.getPassword()));
        User savedUser = userRepository.save(user);
        UserDto userDto = new UserDto();
        userDto.setEmail(savedUser.getEmail());
        userDto.setRoles(savedUser.getRoles());
        return userDto;
    }
    public ResponseEntity<UserDto> login(LoginRequestDto loginDetails) throws NotFoundException, BadCredentialsException {
        Optional<User> savedUser = userRepository.findByEmail(loginDetails.getEmail());
        if(savedUser.isEmpty()){
            throw new NotFoundException("User not found");
        }
        User user = savedUser.get();
        if(!bCryptPasswordEncoder.matches(loginDetails.getPassword(),user.getPassword())){
            throw new BadCredentialsException("Incorrect password");
        }

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());

        String jwtToken = jwtUtil.generateToken(user);

        Session session = new Session();
        session.setToken(jwtToken);
        session.setUser(user);
        session.setStatus(SessionStatus.ACTIVE);
        sessionRepository.save(session);

        MultiValueMapAdapter<String,String> headers = new MultiValueMapAdapter<>(new HashMap<>());

        headers.add("Set-Cookie", "auth-token="+jwtToken);

        return new ResponseEntity<>(userDto, headers, HttpStatus.OK);
    }

    public String logout(String token){
        Optional<Session> savedSession = sessionRepository.findByToken(token);
        if(savedSession.isEmpty())return "Invalid token";
        Session session = savedSession.get();
        session.setStatus(SessionStatus.INACTIVE);
        sessionRepository.save(session);
        return "Logged out successfully";
    }

    public Boolean validateToken(String token){
        return jwtUtil.validateToken(token);
    }
}
