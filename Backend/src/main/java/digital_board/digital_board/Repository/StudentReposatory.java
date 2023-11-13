package digital_board.digital_board.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import digital_board.digital_board.Entity.Student;

public interface StudentReposatory extends JpaRepository<Student,String>{
     
    
} 
