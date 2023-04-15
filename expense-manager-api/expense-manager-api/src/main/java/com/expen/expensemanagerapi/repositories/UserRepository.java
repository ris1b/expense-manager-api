package com.expen.expensemanagerapi.repositories;

import com.expen.expensemanagerapi.domain.User;
import com.expen.expensemanagerapi.exceptions.EtAuthException;

// interacts with dB directly
public interface UserRepository {

    // to return the generated user_id
    Integer create(String firstName, String lastName, String email, String password) throws EtAuthException;

    User findByEmailAndPassword(String email, String password) throws EtAuthException;

    Integer getCountByEmail(String email);

    User findById(Integer userId);


}
