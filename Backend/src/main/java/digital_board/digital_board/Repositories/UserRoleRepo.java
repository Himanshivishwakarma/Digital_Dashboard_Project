package digital_board.digital_board.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import digital_board.digital_board.Entity.UserRole;

public interface UserRoleRepo extends JpaRepository<UserRole,String> {
    
}
