package digital_board.digital_board.Repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import digital_board.digital_board.Entity.Sport;


public interface SportRepository extends JpaRepository<Sport, String>{
    
    List<Sport> findBySportName(String sportName, Sort sort);

    List<Sport> findAll(Sort sort);

    
    
}
