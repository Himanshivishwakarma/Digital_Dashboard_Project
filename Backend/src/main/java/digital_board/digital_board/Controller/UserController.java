package digital_board.digital_board.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import digital_board.digital_board.Entity.User;

import digital_board.digital_board.Servies.ServicesImp.UserServiceImp;


@RestController
public class UserController
{
   @Autowired
   private UserServiceImp userServiceImp;
   
   @PostMapping("/create-user")
   ResponseEntity<User> CreateUser(@RequestBody User user)
   {
      return ResponseEntity.ok(userServiceImp.CreateUser(user));
   }
    
}
