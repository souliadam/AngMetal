package com.esprit.gestionuser.rest;

import com.esprit.gestionuser.persistence.entity.User;
import com.esprit.gestionuser.repository.UserRepository;
import com.esprit.gestionuser.services.Implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userDao;


    @GetMapping({"/hello"})
    public String hello() {
        return "hello uesr service";
    }

    @GetMapping({"/users"})
    public List<User> getAll() {
        return userService.getAll();
    }


    @GetMapping({"{userName}"})
    public User findOne(@PathVariable String userName) {
        return userService.findOne(userName);
    }


    @GetMapping("/count")
    public long count() {
        return userService.count();
    }

    @GetMapping("/countoperateur")
    public long countoperateur() {
        return userService.countoperateur();
    }

    @GetMapping("/countadmin")
    public long countadmin() {
        return userService.countadmin();
    }


    @GetMapping("/countusers")
    public long countusers() {
        return userService.countusers();
    }

}