// package digital_board.digital_board.Repository;
// import static org.junit.jupiter.api.Assertions.assertEquals;

// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import digital_board.digital_board.Entity.User;




// @SpringBootTest
// public class UserRepositoryTest
// {
//  @Autowired
//  private UserRepository userRepository;
//     @Test
//     void findByEmail() 
//     {
       
//        User user=new User("1","ayan","ayan@gmail.com","student","iteg","image");
//        userRepository.save(user);
//        User userDetails =userRepository.findByEmail("ayan@gmail.com").orElseThrow();
//        assertEquals("ayan@gmail.com",userDetails.getEmail());
//    }

  
//    @AfterEach
//    void tearDown()
//    {
//       userRepository.deleteById("1");
//    }


// }