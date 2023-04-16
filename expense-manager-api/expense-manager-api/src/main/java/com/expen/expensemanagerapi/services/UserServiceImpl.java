package com.expen.expensemanagerapi.services;

import com.expen.expensemanagerapi.domain.User;
import com.expen.expensemanagerapi.exceptions.EtAuthException;
import com.expen.expensemanagerapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public User validateUser(String email, String password) throws EtAuthException {
        if(email != null) email = email.toLowerCase();

        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User registerUser(String firstName, String lastnName, String email, String password) throws EtAuthException {
        // Match the email format
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");

        // convert the given email to lower-case:
        if(email != null) email = email.toLowerCase();

        // test the email format
        if(!pattern.matcher(email).matches()){
            throw new EtAuthException("Invalid e-mail format");
        }

        // invoke userRepository and get the count of email !
        Integer count = userRepository.getCountByEmail(email);

        if(count > 0){
            throw new EtAuthException("Email is already in use");
        }

        // get the generated PK for the user.
        Integer userId = userRepository.create(firstName, lastnName, email, password);

        // fetch user by id, and return !
        User user = userRepository.findById(userId);

        return user;
    }
}
