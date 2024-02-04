package com.example.MiApi;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {


    @Autowired
    UserDAO userDAO;

    public List<User> readAllUsers() {
        return userDAO.findAll();
    }

    public User getUserById(Integer id) {
        Optional<User> optionalUser;
        optionalUser= userDAO.findById(id);
        return optionalUser.orElse(null);
    }

    public User addUser(User user) {
       return userDAO.save(user);
    }

    public void removeUser(Integer id) {
        userDAO.deleteById(id);
    }

    public User updateUser(Integer id, User user){
        Optional<User> optionalUser = userDAO.findById(id);
        if (optionalUser.isPresent()){
            User existe = optionalUser.get();
            existe.setFullName(user.getFullName());
            existe.setPassword(user.getPassword());
            existe.setEmail(user.getEmail());
            return  userDAO.save(existe);

        }
        return null;

    }
    public User userUpdate(Integer id, Map<String,Object> updates){
        Optional<User> optionalUser = userDAO.findById(id);
        if (optionalUser.isPresent()){
            User existe = optionalUser.get();
            if (updates.containsKey("fullName")){
                existe.setFullName((String) updates.get("fullName"));
            }if (updates.containsKey("password")){
                existe.setPassword((String) updates.get("password"));
            }if (updates.containsKey("email")){
                existe.setEmail((String) updates.get("email"));
            }
            return userDAO.save(existe);

        }return null;
    }
    public User userUpdatePatch(Integer id, Map<String,Object> updates){
        Optional<User> optionalUser = userDAO.findById(id);
        if (optionalUser.isPresent()){
            User existe = optionalUser.get();
            if (updates.containsKey("fullName")){
                existe.setFullName((String) updates.get("fullName"));
            }if (updates.containsKey("password")){
                existe.setPassword((String) updates.get("password"));
            }if (updates.containsKey("email")){
                existe.setEmail((String) updates.get("email"));
            }
            return userDAO.save(existe);

        }return null;
    }
}
