package com.zoro.userservice.services;

import com.zoro.userservice.dtos.LoginRequestDto;
import com.zoro.userservice.dtos.TokenDto;
import com.zoro.userservice.dtos.SignUpRequestDto;
import com.zoro.userservice.dtos.UserDto;
import com.zoro.userservice.exceptions.NotFoundException;
import com.zoro.userservice.models.Session;
import com.zoro.userservice.models.User;
import com.zoro.userservice.repositories.SessionRepository;
import com.zoro.userservice.repositories.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Primary
@Service
public class AuthServiceImpl implements AuthService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*abcdefghijklmnopqrstuvwxyz";
    public AuthServiceImpl(UserRepository userRepository, SessionRepository sessionRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    private String getRandomToken(){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 30; i++){
            sb.append(candidateChars.charAt(random.nextInt(candidateChars.length())));
        }
        return sb.toString();
    }

    @Override
    public UserDto signup(SignUpRequestDto signUpRequestDto) {
        User user = new User();
        user.setEmail(signUpRequestDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(signUpRequestDto.getPassword()));
        User savedUser = userRepository.save(user);
        UserDto userDto = new UserDto();
        userDto.setEmail(savedUser.getEmail());
        return userDto;
    }
    public TokenDto login(LoginRequestDto loginDetails) throws NotFoundException {
        User user = userRepository.findByEmail(loginDetails.getEmail());
        if (user == null){
            throw new NotFoundException("User not found");
        }
        if(!bCryptPasswordEncoder.matches(loginDetails.getPassword(),user.getPassword())){
            //TODO: Create a proper exception to throw here
            throw new NotFoundException("Incorrect password");
        }
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(getRandomToken());
        Session session = new Session();
        session.setToken(tokenDto.getToken());
        session.setUser(user);
        sessionRepository.save(session);
        return tokenDto;
    }

    public String logout(TokenDto tokenDto){
        Session session = sessionRepository.findByToken(tokenDto.getToken());
        if(session==null)return "Invalid token";
        sessionRepository.delete(session);
        return "Logged out successfully";
    }
}
