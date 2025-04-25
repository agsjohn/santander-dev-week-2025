package me.dio.domain.controller;

import me.dio.domain.model.User;
import me.dio.domain.service.UserService;
import org.springframework.context.weaving.LoadTimeWeaverAware;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final LoadTimeWeaverAware loadTimeWeaverAware;

    public UserController(UserService userService, LoadTimeWeaverAware loadTimeWeaverAware){
        this.userService = userService;
        this.loadTimeWeaverAware = loadTimeWeaverAware;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        var user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User userToCreate){
        var userCreated = userService.create(userToCreate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(userCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User userUpdated){
        userService.update(id, userUpdated);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userUpdated.getId())
                .toUri();
        return ResponseEntity.created(location).body(userUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.ok(null);
    }
}
