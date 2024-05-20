package org.defalt.content.service;

import org.defalt.content.context.CurrentApplicationContext;
import org.defalt.content.entity.User;
import org.defalt.content.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public static UserService getInstance() {
        return CurrentApplicationContext.getBean(UserService.class);
    }

    public User getOrCreate(String username) {
        return repository.getByUsername(username)
                .orElseGet(() -> create(username));
    }

    public User create(String username) {
        User user = new User();
        user.setUsername(username);
        return repository.save(user);
    }
}
