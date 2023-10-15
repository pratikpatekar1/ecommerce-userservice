package com.zoro.userservice.services;

import com.zoro.userservice.dtos.LoginDto;
import com.zoro.userservice.dtos.TokenDto;
import com.zoro.userservice.dtos.UserRegistrationDto;
import com.zoro.userservice.exceptions.NotFoundException;
import com.zoro.userservice.models.Session;
import com.zoro.userservice.models.User;
import com.zoro.userservice.repositories.SessionRepository;
import com.zoro.userservice.repositories.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;

@Primary
@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*abcdefghijklmnopqrstuvwxyz";
    public UserServiceImpl(UserRepository userRepository, SessionRepository sessionRepository){
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
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
    public User createUser(UserRegistrationDto userRegistrationDto) {
        User user = new User();
        user.setEmail(userRegistrationDto.getEmail());
        user.setEncPassword(userRegistrationDto.getPassword());
        User savedUser = userRepository.save(user);
        return savedUser;
    }
    public TokenDto login(LoginDto loginDetails) throws NotFoundException {
        User user = userRepository.findByEmail(loginDetails.getEmail());
        if (user == null){
            throw new NotFoundException("User not found");
        }
        if(!Objects.equals(user.getEncPassword(), loginDetails.getPassword())){
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
