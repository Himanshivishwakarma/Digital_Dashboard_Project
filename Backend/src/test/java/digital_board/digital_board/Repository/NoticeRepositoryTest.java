// package digital_board.digital_board.Repository;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// import java.util.List;
// import java.util.Optional;

// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.boot.test.context.SpringBootTest;

// import digital_board.digital_board.Entity.Notice;


// @SpringBootTest
// public class NoticeRepositoryTest {

//     @Autowired
//     private NoticeRepository noticeRepository;

//     @Test
//     void findById() {

//         Notice notice = new Notice("1", "first instalment", "your first instalment date----", "Account", "Iteg",
//                 " noticeStartDate", "noticeEndDate", "noticeCreatedDate", "sultan", true);

//         noticeRepository.save(notice);

//         Notice findById = noticeRepository.findById("1").orElseThrow();

//         assertEquals(notice, findById);

//     }

//     @Test
//     public void testGetAllNoticeByUserId() {
//         // Given
//         String userId = "testUser";
//         Notice notice1 = new Notice("1", "first instalment", "your first instalment date----", "Account", "Iteg",
//                 " noticeStartDate", "noticeEndDate", "noticeCreatedDate", userId, true);
//         Notice notice2 = new Notice("2", "first instalment", "your first instalment date----", "Account", "Iteg",
//                 " noticeStartDate", "noticeEndDate", "noticeCreatedDate", userId, true);

//         // Save notices to the database
//         noticeRepository.saveAll(List.of(notice1, notice2));

//         // When
//         List<Notice> notices = noticeRepository.getAllNoticeByUserId(userId);

//         // Then
//         assertEquals(2, notices.size()); 
//     }

//     @AfterEach
//     void tearDown() {
//         noticeRepository.deleteById("1");
//         noticeRepository.deleteById("2");
//     }

// }