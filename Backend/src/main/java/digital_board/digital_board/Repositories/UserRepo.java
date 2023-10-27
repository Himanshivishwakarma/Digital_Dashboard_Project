package digital_board.digital_board.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import digital_board.digital_board.Entity.User;

public interface UserRepo extends JpaRepository<User,String>{

    Optional findByEmail(String username);
    
}
