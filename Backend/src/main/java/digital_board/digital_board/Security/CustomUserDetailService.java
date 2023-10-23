package digital_board.digital_board.Security; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import digital_board.digital_board.Entity.User;
import digital_board.digital_board.Repositories.UserRepo;




@Service
public class CustomUserDetailService implements UserDetailsService{

      @Autowired
      private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //    loading user from databases by username
      User user = (User) this.userRepo.findByEmail(username).orElseThrow();
        //  User user=this.userRepo.findByUsername(username).orElse(null);
        return (UserDetails) user;
    }

    
    
}