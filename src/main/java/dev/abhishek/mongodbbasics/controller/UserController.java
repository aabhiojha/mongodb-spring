package dev.abhishek.mongodbbasics.controller;

import com.fasterxml.jackson.core.ObjectCodec;
import dev.abhishek.mongodbbasics.entity.User;
import dev.abhishek.mongodbbasics.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ObjectCodec objectCodec;

    @GetMapping
    public List<User> getAlUser() {
        return userService.getAll();
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.saveEntry(user);
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String username) {
        User userObj = userService.findByUserName(username);
        if (userObj != null) {
            userObj.setUserName(user.getUserName());
            userObj.setPassword(user.getPassword());
            userService.saveEntry(userObj);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
