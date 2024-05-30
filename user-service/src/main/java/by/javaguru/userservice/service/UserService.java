package by.javaguru.userservice.service;

import by.javaguru.userservice.service.event.UserGetEvent;

import java.util.concurrent.ExecutionException;

public interface UserService {
    UserGetEvent getUserById(Long id) throws ExecutionException, InterruptedException;
}
