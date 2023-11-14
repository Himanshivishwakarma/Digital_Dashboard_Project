package digital_board.digital_board.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import digital_board.digital_board.Entity.Notice;
import digital_board.digital_board.Entity.User;

@SpringBootTest
public class NoticeRepositoryTest {

    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    void findById() 
    {
        
        Notice notice = new Notice("1", "first instalment", "your first instalment date----", "Account", "Iteg",
                " noticeStartDate", "noticeEndDate", "noticeCreatedDate", "sultan", true);

        noticeRepository.save(notice);

        Notice findById = noticeRepository.findById("1").orElseThrow();

        assertEquals(notice, findById);

    }

    @AfterEach
    void tearDown() {
    noticeRepository.deleteById("1");
    }

}