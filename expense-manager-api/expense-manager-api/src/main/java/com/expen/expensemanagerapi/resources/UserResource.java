package com.expen.expensemanagerapi.resources;

import com.expen.expensemanagerapi.domain.User;
import com.expen.expensemanagerapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserResource {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody  Map<String, Object> userMap){
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");

        // validate the user
        User user = userService.validateUser(email, password);

        Map<String , String> map = new HashMap<>();         // here also generate a JWT token
        map.put("message", "Logged in successfully");

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Map<String, Object> userMap){
        String firstname = (String) userMap.get("firstname");
        String lastname = (String) userMap.get("lastname");
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");

//        return firstname + ", " + lastname + ", " + email + ", " + password ;
        User user = userService.registerUser(firstname,lastname, email, password);

        Map<String, String> map = new HashMap<>();
        map.put("message", "registration successfull");     // send JWT Token from here ?

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
