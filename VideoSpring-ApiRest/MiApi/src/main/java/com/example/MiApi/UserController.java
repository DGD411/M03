package com.example.MiApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Controller
public class UserController {

   @Autowired
   UserService userService;


    public List<UserDto> readAll() {

        return userService.readAllUsers().stream().map(UserDto::new).toList();
    }

    public UserDto getUserById(Integer id) {
       return new UserDto( userService.getUserById(id));

    }

    public UserDto addUser(User user) {
        User u = userService.addUser(user);
        return new UserDto(u);
    }

    public void removeUSer(Integer id) {
        userService.removeUser(id);
    }
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id, @RequestBody User user){
        User updateUser = userService.updateUser(id, user);
        if (updateUser != null){
            return ResponseEntity.ok(new UserDto(updateUser));
        }
        return ResponseEntity.notFound().build();
    }
    public User userUpdate(Integer id, Map<String, Object> updates){
        return  userService.userUpdate(id, updates);
    }
    public User userUpdatePatch(Integer id, Map<String, Object> updates){
        return  userService.userUpdatePatch(id, updates);
    }

}
