package digital_board.digital_board.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import digital_board.digital_board.Entity.Student;
import digital_board.digital_board.ServiceImpl.StudentServiceImpl;
import jakarta.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/student/")
public class StudentController {

    @Autowired
    StudentServiceImpl studentServiceImpl;

    @PostMapping("/add")
    public ResponseEntity<Student> addStudent(@Valid @RequestBody Student student) {
        Student student2 = studentServiceImpl.addStudent(student);
        return new ResponseEntity<Student>(student2, HttpStatus.CREATED);
    }

     @GetMapping("/getAll")
    public ResponseEntity<List<Student>> getAllStudent() {
       List<Student> student2 = studentServiceImpl.getAllStudent();
        return new ResponseEntity<List<Student>>(student2, HttpStatus.ACCEPTED);
    }

}