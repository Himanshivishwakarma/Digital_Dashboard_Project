package digital_board.digital_board.Entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity

@Table(name = "studentInfo")
public class Student {
    @Id
    String student_id = UUID.randomUUID().toString();
    
    // @NotBlank(message = "Name is mandatory")
    String student_name;

    @Email(regexp = ".*@ssism.org", message = "Email must be from ssism.org domain")
    String student_email;

    String student_group;

    String student_mobile_no;

    String created_At;

    String updateAt;

}