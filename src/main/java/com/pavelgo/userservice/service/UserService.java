package com.pavelgo.userservice.service;

import com.pavelgo.userservice.model.User;
import com.pavelgo.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final List<UserRepository> userRepositories;

    public List<User> getUsers() {
        return userRepositories.stream()
                .flatMap(userRepository -> userRepository.findAll().stream())
                .toList();
    }

    public List<User> getUsers(String id, String username, String name, String surname){
        return userRepositories.stream()
                .flatMap(userRepository -> userRepository.findAll(Map.of("id", id,
                        "username", username, "name", name, "surname", surname)).stream())
                .toList();
    }
}
