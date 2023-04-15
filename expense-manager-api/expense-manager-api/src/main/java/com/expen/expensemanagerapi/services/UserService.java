package com.expen.expensemanagerapi.services;

import com.expen.expensemanagerapi.domain.User;
import com.expen.expensemanagerapi.exceptions.EtAuthException;

public interface UserService {

    // validate an existing user
    User validateUser(String email, String password) throws EtAuthException;

    // register user
    User registerUser(String firstName, String lastnName,String email, String password) throws EtAuthException;

}
