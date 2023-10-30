package digital_board.digital_board.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import digital_board.digital_board.Entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, String>{

    
}
