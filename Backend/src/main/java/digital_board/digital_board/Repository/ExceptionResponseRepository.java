package digital_board.digital_board.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import digital_board.digital_board.Entity.ExceptionResponse;

public interface ExceptionResponseRepository extends JpaRepository<ExceptionResponse,String>{


}
