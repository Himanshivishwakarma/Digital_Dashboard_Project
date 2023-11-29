package digital_board.digital_board.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// import org.springframework.data.jpa.repository.JpaRepository;

import digital_board.digital_board.Entity.User;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

     @Query("Select u from User u Where u.email =:email")
    User getbyemail(@Param("email") String email);
    

}
