package digital_board.digital_board.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import digital_board.digital_board.Entity.Sport;

public interface SportRepository extends JpaRepository<Sport, String>{
    
}
