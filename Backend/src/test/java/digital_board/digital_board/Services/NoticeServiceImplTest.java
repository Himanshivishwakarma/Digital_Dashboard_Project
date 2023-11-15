package digital_board.digital_board.Services;

import digital_board.digital_board.Entity.Notice;
import digital_board.digital_board.Repository.NoticeRepository;
import digital_board.digital_board.ServiceImpl.NoticeServiceImpl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
public class NoticeServiceImplTest {

    @Mock
    private NoticeRepository noticeRepository;

    @InjectMocks
    private NoticeServiceImpl noticeService;
    String userId = "testUser";

    @Test
    public void testCreateNoticeByUser() {
    

        Notice inputNotice = new Notice("1", "first instalment", "your first instalment date----", "Account", "Iteg",
                " noticeStartDate", "noticeEndDate", "noticeCreatedDate", userId, true);
        Notice savedNotice = new Notice("2", "first instalment", "your first instalment date----", "Account", "Iteg",
                " noticeStartDate", "noticeEndDate", "noticeCreatedDate", userId, true);

        Mockito.when(noticeRepository.save(any(Notice.class))).thenReturn(savedNotice);

      
        Notice result = noticeService.createNoticeByUser(inputNotice);

        
        assertEquals(savedNotice, result);
    
    }

    @Test
    public void testGetNoticeByNoticeId() {
     
        String noticeId = "123";
        Notice foundNotice = new Notice(noticeId, "first instalment", "your first instalment date----", "Account", "Iteg",
                " noticeStartDate", "noticeEndDate", "noticeCreatedDate", userId, true);

        Mockito.when(noticeRepository.findById(eq(noticeId))).thenReturn(Optional.of(foundNotice));

    
        Notice result = noticeService.getNoticeByNoticeId(noticeId);

        
        assertEquals(foundNotice, result);
      
    }

    @Test
    public void testGetNoticeByUserId() {
      
        String userId = "testUser";
        List<Notice> expectedNotices = Arrays.asList(new Notice("123", "first instalment", "your first instalment date----", "Account", "Iteg",
                " noticeStartDate", "noticeEndDate", "noticeCreatedDate", userId, true));

        Mockito.when(noticeRepository.getAllNoticeByUserId(eq(userId))).thenReturn(expectedNotices);

       
        List<Notice> result = noticeService.getNoticeByUserId(userId);

       
        assertEquals(expectedNotices, result);
      
    }

    @Test
    public void testGetAllNotice() {
        
        List<Notice> expectedNotices = Arrays.asList(new Notice("123", "first instalment", "your first instalment date----", "Account", "Iteg",
                " noticeStartDate", "noticeEndDate", "noticeCreatedDate", userId, true),new Notice("1234", "first instalment", "your first instalment date----", "Account", "Iteg",
                " noticeStartDate", "noticeEndDate", "noticeCreatedDate", userId, true));

        Mockito.when(noticeRepository.findAll()).thenReturn(expectedNotices);

    
        List<Notice> result = noticeService.getAllNotice();

        
        assertEquals(expectedNotices, result);
        
    }
}
