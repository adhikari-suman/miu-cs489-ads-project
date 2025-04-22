package edu.miu.cs489.adswebapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/{username}")
    public ResponseEntity<Object> getUserByUsername(
            @PathVariable("username") String username
                                                   ) {
        return ResponseEntity.ok("User with username: " + username + " found.");
    }

    @PatchMapping("/{username}/credentials")
    public ResponseEntity<Object> updateCredentialsForUsername(
            @PathVariable("username") String username
                                                   ) {
        return ResponseEntity.ok("User with username: " + username + " found.");
    }
}
