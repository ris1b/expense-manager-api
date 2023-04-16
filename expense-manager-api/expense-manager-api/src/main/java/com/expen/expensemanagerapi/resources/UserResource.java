package com.expen.expensemanagerapi.resources;

import com.expen.expensemanagerapi.Constants;
import com.expen.expensemanagerapi.domain.User;
import com.expen.expensemanagerapi.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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
        if (user == null) {
            System.out.println("User is null");
        }
        Map<String , String> token = generateJWTToken(user);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Map<String, Object> userMap){
        String firstname = (String) userMap.get("firstname");
        String lastname = (String) userMap.get("lastname");
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");

        User user = userService.registerUser(firstname,lastname, email, password);
        Map<String , String> token = generateJWTToken(user);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }


    private Map<String, String> generateJWTToken(User user) {
        long timestamp = System.currentTimeMillis();
        String token = Jwts.                // generate tokens
                builder()                   // configure the tokens
                .signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)       // Signature Algo
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                .claim("userId", user.getUserId())
                .claim("email", user.getEmail())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .compact();                 // builds the JWT and serializes it to a compact, URL-safe string

        Map<String, String> map = new HashMap<>();
        map.put("token", token);

        return map;
    }
}
