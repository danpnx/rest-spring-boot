package com.project.store.services;

import com.project.store.entities.User;
import com.project.store.repositories.UserRepository;
import com.project.store.services.exceptions.DatabaseException;
import com.project.store.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {

        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public User insertUser(User obj) {
        return userRepository.save(obj);
    }

    public void deleteUser(Long id) {
        try{
            userRepository.deleteById(id);
        } catch(EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch(DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public User updateUser(Long id, User obj) {
        try {
            User entity = userRepository.getReferenceById(id);
            BeanUtils.copyProperties(obj, entity);
            return userRepository.save(entity);
        } catch(EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }
}
