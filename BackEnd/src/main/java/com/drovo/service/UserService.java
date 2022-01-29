package com.drovo.service;

import com.drovo.Repositery.UserRepo;
import com.drovo.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    public boolean registerUser(Person user){
        if(user.getPhoneNo() == null || userRepo.existsById(user.getPhoneNo())) return false;
        if (user.getPhoneNo().toString().length() != 10) return false;
        if (user.getPassword() == null) return false;
        if (user.getName() == null) return false;
        Person savedUser = userRepo.save(user);
        if(savedUser != null) return true;
        else                  return false;
    }

    public boolean logInUser(Person user){
        Optional<Person> optional = userRepo.findById(user.getPhoneNo());
        if (optional.isEmpty()) return false; // check exist;
        if (optional.get().getPassword().equals(user.getPassword())) return true; // matching user;
        else return false;
    }


    public List<Person> getAllUsers(){
        return userRepo.findAll();
    }

}
