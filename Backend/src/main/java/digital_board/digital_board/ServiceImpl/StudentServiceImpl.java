package digital_board.digital_board.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import digital_board.digital_board.Entity.Student;
import digital_board.digital_board.Repository.StudentReposatory;
import digital_board.digital_board.Servies.StudentService;
import java.util.List;


@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentReposatory studentReposatory;

    @Override
    public Student addStudent(Student student) {

        return studentReposatory.save(student);
    }

    @Override
    public List<Student> getAllStudent() {
          List<Student> students = studentReposatory.findAll();
          return students;
    }
}