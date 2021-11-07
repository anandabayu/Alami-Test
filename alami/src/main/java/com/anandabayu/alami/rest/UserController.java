package com.anandabayu.alami.rest;

import com.anandabayu.alami.entity.User;
import com.anandabayu.alami.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        HashMap<String, Object> map = new HashMap<>();

        List<User> users = userService.findAll();

        map.put("success", true);
        map.put("data", users);

        return ResponseEntity.ok(map);
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        HashMap<String, Object> map = new HashMap<>();

        if (user.getName().length() <= 3) {
            map.put("success", false);
            map.put("message", "User name should more then 3 character");
            map.put("data", null);

            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        user.setId(0);
        userService.create(user);

        map.put("success", true);
        map.put("message", "Success create user");
        map.put("data", user);

        return ResponseEntity.ok(map);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable int id) {
        HashMap<String, Object> map = new HashMap<>();

        User user = userService.findById(id);

        if (user == null) {
            map.put("success", false);
            map.put("message", "User not found");
            map.put("data", null);

            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }

        map.put("success", true);
        map.put("message", "User found");
        map.put("data", user);

        return ResponseEntity.ok(map);
    }

}
