package ru.otus.services;

import org.springframework.stereotype.Service;
import ru.otus.dao.UserRepository;
import ru.otus.model.User;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userDao;

    public UserServiceImpl(UserRepository userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<User> getUserInfo(Long userId) {
        return userDao.findById(userId);
    }

    @Override
    public Optional<User> getRandomUser() {
        return userDao.findRandomUser();
    }
}