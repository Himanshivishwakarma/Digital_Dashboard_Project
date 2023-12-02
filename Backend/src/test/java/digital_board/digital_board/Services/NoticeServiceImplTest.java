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
import java.util.Date;
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
    String noticeId = "Important Notice";

    @Test
    public void testCreateNoticeByUser() {

        Notice myNotice = new Notice(noticeId, "This is an important announcement.", "this is notice descriptions",
                "General", "HR Department", "2023-11-01", "2023-11-10", new Date(), "John Doe", "enable");

        Notice myNotice1 = new Notice("Important Notice1", "This is an important announcement.",
                "this is notice descriptions",
                "General", "HR Department", "2023-11-01", "2023-11-10", new Date(), "John Doe", "enable");

        Mockito.when(noticeRepository.save(any(Notice.class))).thenReturn(myNotice);

        Notice result = noticeService.createNoticeByUser(myNotice1);

        assertEquals(myNotice, result);

    }

    @Test
    public void testGetNoticeByNoticeId() {

        Notice myNotice = new Notice(noticeId, "This is an important announcement.", "this is notice descriptions",
                "General", "HR Department", "2023-11-01", "2023-11-10", new Date(), "John Doe", "enable");

        Mockito.when(noticeRepository.findById(eq(noticeId))).thenReturn(Optional.of(myNotice));

        Notice result = noticeService.getNoticeByNoticeId(noticeId);

        assertEquals(myNotice, result);

    }

    @Test
    public void testGetNoticeByUserId() {

        List<Notice> myNotice = Arrays.asList(
                new Notice(noticeId, "This is an important announcement.", "this is notice descriptions", "General",
                        "HR Department", "2023-11-01", "2023-11-10", new Date(), userId, "important"),
                new Notice(noticeId, "This is an important announcement.", "this is notice descriptions", "General",
                        "HR Department", "2023-11-01", "2023-11-10", new Date(), userId, "important"));

        Mockito.when(noticeRepository.getAllNoticeByUserId(eq(userId))).thenReturn(myNotice);

        List<Notice> result = noticeService.getNoticeByUserEmail(userId);

        assertEquals(myNotice, result);

    }

    @Test
    public void testGetAllNotice() {

       List<Notice> myNotice = Arrays.asList(
                new Notice(noticeId, "This is an important announcement.", "this is notice descriptions", "General",
                        "HR Department", "2023-11-01", "2023-11-10", new Date(), userId, "enable"),
                new Notice(noticeId, "This is an important announcement.", "this is notice descriptions", "General",
                        "HR Department", "2023-11-01", "2023-11-10", new Date(), userId, "enable"));


    Mockito.when(noticeRepository.findAll()).thenReturn(myNotice);

    List<Notice> result = noticeService.getAllNotice();

    assertEquals(myNotice, result);

    }

}
