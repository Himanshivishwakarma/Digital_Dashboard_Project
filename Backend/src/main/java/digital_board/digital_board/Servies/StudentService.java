package digital_board.digital_board.Servies;

import digital_board.digital_board.Entity.Student;

import java.util.List;

public interface StudentService {


    Student addStudent(Student student);
     
   List<Student> getAllStudent();
    
} 