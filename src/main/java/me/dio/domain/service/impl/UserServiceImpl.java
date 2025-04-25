package me.dio.domain.service.impl;

import me.dio.domain.model.User;
import me.dio.domain.repository.UserRepository;
import me.dio.domain.service.UserService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public User create(User userToCreate) {
        if(userRepository.existsByAccountNumber(userToCreate.getAccount().getNumber())){
            throw new IllegalArgumentException("This Account number already exists.");
        }
        if(userRepository.existsByCardNumber(userToCreate.getCard().getNumber())){
            throw new IllegalArgumentException("This Card number already exists.");
        }
        return userRepository.save(userToCreate);
    }

    @Override
    public void delete(Long id) {
        if(!userRepository.existsById(id)){
            throw new NoSuchElementException();
        } else {
            userRepository.deleteById(id);
        }
    }

    @Override
    public void update(Long id, User userToUpdate) {
        if(!userRepository.existsById(id)){
            throw new NoSuchElementException();
        } else{
            User userUpdated = this.findById(id);
            userUpdated.setName(userToUpdate.getName());
            userUpdated.setAccount(userToUpdate.getAccount());
            userUpdated.setCard(userToUpdate.getCard());
            userUpdated.setFeatures(userToUpdate.getFeatures());
            userUpdated.setNews(userToUpdate.getNews());
            userRepository.save(userUpdated);
        }
    }
}
