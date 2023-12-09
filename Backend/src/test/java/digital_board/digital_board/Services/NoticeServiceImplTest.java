package digital_board.digital_board.Services;

import digital_board.digital_board.Entity.Notice;
import digital_board.digital_board.Repository.NoticeRepository;
import digital_board.digital_board.ServiceImpl.NoticeServiceImpl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

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

                Notice myNotice = new Notice(noticeId, "This is an important announcement.",
                                "this is notice descriptions",
                                "General", "HR Department", "2023-11-01", "2023-11-10", new Date(), "John Doe",
                                "enable");

                Notice myNotice1 = new Notice("Important Notice1", "This is an important announcement.",
                                "this is notice descriptions",
                                "General", "HR Department", "2023-11-01", "2023-11-10", new Date(), "John Doe",
                                "enable");

                Mockito.when(noticeRepository.save(any(Notice.class))).thenReturn(myNotice);

                Notice result = noticeService.createNoticeByUser(myNotice1);

                assertEquals(myNotice, result);

        }

        @Test
        public void testGetNoticeByNoticeId() {

                Notice myNotice = new Notice(noticeId, "This is an important announcement.",
                                "this is notice descriptions",
                                "General", "HR Department", "2023-11-01", "2023-11-10", new Date(), "John Doe",
                                "enable");

                Mockito.when(noticeRepository.findById(eq(noticeId))).thenReturn(Optional.of(myNotice));

                Notice result = noticeService.getNoticeByNoticeId(noticeId);

                assertEquals(myNotice, result);

        }

        @Test
        public void testGetNoticeByUserId() {

                List<Notice> myNotices = Arrays.asList(
                                new Notice(noticeId, "This is an important announcement.",
                                                "this is notice descriptions", "General",
                                                "HR Department", "2023-11-01", "2023-11-10", new Date(), userId,
                                                "important"),
                                new Notice(noticeId, "This is an important announcement.",
                                                "this is notice descriptions", "General",
                                                "HR Department", "2023-11-01", "2023-11-10", new Date(), userId,
                                                "important"));

                Page<Notice> noticePage = new PageImpl<>(myNotices);
                Mockito.when(noticeRepository.getAllNoticeByUserId(Mockito.any(String.class),
                                Mockito.any(Pageable.class))).thenReturn(noticePage);

                Page<Notice> result = noticeService.getNoticeByUserEmail(userId, PageRequest.of(0, 10));

                assertEquals(noticePage, result);

        }

        @Test
        public void testGetAllNotice() {

                List<Notice> myNotice = Arrays.asList(
                                new Notice(noticeId, "This is an important announcement.",
                                                "this is notice descriptions", "General",
                                                "HR Department", "2023-11-01", "2023-11-10", new Date(), userId,
                                                "enable"),
                                new Notice(noticeId, "This is an important announcement.",
                                                "this is notice descriptions", "General",
                                                "HR Department", "2023-11-01", "2023-11-10", new Date(), userId,
                                                "enable"));

                Mockito.when(noticeRepository.findAll()).thenReturn(myNotice);

                List<Notice> result = noticeService.getAllNotice();

                assertEquals(myNotice, result);

        }

        @Test
        public void testGetNoticesByCategory() {
                List<String> myNotices = Arrays.asList("General", "General", "General");

                List<Notice> noticeList = myNotices.stream()
                                .map(content -> new Notice("1", "This is an important announcement.",
                                                "this is notice descriptions", content,
                                                "HR Department", "2023-11-01", "2023-11-10", new Date(), userId,
                                                "enable"))
                                .collect(Collectors.toList());

                Page<Notice> noticePage = new PageImpl<>(noticeList);
                Mockito.when(noticeRepository.findByCategoryIn(anyList(), any(Pageable.class))).thenReturn(noticePage);

                Page<Notice> result = noticeService.getNoticesByCategory(myNotices, PageRequest.of(0, 10));

                assertEquals(noticePage, result);

        }

        // no
        @Test
        public void testGetNoticesByDepartment() {
                List<String> myNotices = Arrays.asList("HR Department", "HR Department", "HR Department",
                                "HR Department");

                List<Notice> noticeList = myNotices.stream()
                                .map(content -> new Notice("1", "This is an important announcement.",
                                                "this is notice descriptions", "general",
                                                content, "2023-11-01", "2023-11-10", new Date(), userId,
                                                "enable"))
                                .collect(Collectors.toList());

                Page<Notice> noticePage = new PageImpl<>(noticeList);
                Mockito.when(noticeRepository.findByDepartmentNameIn(anyList(), any(Pageable.class)))
                                .thenReturn(noticePage);

                Page<Notice> result = noticeService.getNoticesByCategory(myNotices, PageRequest.of(0, 10));

                assertEquals(noticePage, result);

        }

        @Test
        public void getAllNoticesSorted() {
                List<Notice> myNoticesList = Arrays.asList(
                                new Notice(noticeId, "This is an important announcement.",
                                                "this is notice descriptions", "General",
                                                "HR Department", "2023-11-01", "2023-11-10", new Date(), userId,
                                                "enable"),
                                new Notice(noticeId, "This is an important announcement.",
                                                "this is notice descriptions", "General",
                                                "HR Department", "2023-11-01", "2023-11-10", new Date(), userId,
                                                "enable"));
                Page<Notice> noticePage = new PageImpl<>(myNoticesList);
                when(noticeRepository.findAll(any(Pageable.class))).thenReturn(noticePage);

                Page<Notice> result = noticeService.getAllNoticesSorted(PageRequest.of(0, 10));
                assertEquals(noticePage, result);
        }

        @Test
        public void testSearchNotices() {
                List<Notice> myNoticesList = Arrays.asList(
                                new Notice(noticeId, "This is an important announcement.",
                                                "this is notice descriptions", "General",
                                                "HR Department", "2023-11-01", "2023-11-10", new Date(), userId,
                                                "enable"),
                                new Notice(noticeId, "This is an important announcement.",
                                                "this is notice descriptions", "General",
                                                "HR Department", "2023-11-01", "2023-11-10", new Date(), userId,
                                                "enable"));
                Page<Notice> noticePages = new PageImpl<>(myNoticesList);
                when(noticeRepository.findByNoticeTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                                anyString(), anyString(), any(Pageable.class))).thenReturn(noticePages);

                Page<Notice> resultPage = noticeService.searchNotices(noticeId, PageRequest.of(0, 10));
                assertEquals(noticePages, resultPage);
        }

        @Test
        public void testGetAllImportantNotice() {
                String status = "important";
                int limit = 3;
                List<Notice> myNoticesList = Arrays.asList(
                                new Notice(noticeId, "This is an important announcement.",
                                                "this is notice descriptions", "General",
                                                "HR Department", "2023-11-01", "2023-11-10", new Date(), userId,
                                                status),
                                new Notice(noticeId, "This is an important announcement.",
                                                "this is notice descriptions", "General",
                                                "HR Department", "2023-11-01", "2023-11-10", new Date(), userId,
                                                status));
                when(noticeRepository.findNoticesWithLimit(limit, status)).thenReturn(myNoticesList);
                List<Notice> resultNotices = noticeService.getAllImportantNotice(limit);
                assertEquals(myNoticesList, resultNotices);
        }
        @Test
        public void testgGetTotalNoticeCount() {
                long expectedCount = 10L;
          when(noticeRepository.count()).thenReturn(expectedCount);
         long number = noticeService.getTotalNoticeCount();
         assertEquals(expectedCount, number);
                        }
         @Test
        public void testCountByCategory() {
                long expectedCount = 10L;
                String category = "sport";
          when(noticeRepository.countByCategory(anyString())).thenReturn(expectedCount);
         long number = noticeService.countByCategory(category);
         assertEquals(expectedCount, number);
}
  @Test
        public void testCountByDepartmentName() {
                long expectedCount = 10L;
                String departmentName = "iteg";
          when(noticeRepository.countByDepartmentName(anyString())).thenReturn(expectedCount);
         long number = noticeService.countByDepartmentName(departmentName);
         assertEquals(expectedCount, number);
}
  @Test
  public void testNoticefindByStatusImportant(){
        String status = "enable";
        Sort sort = Sort.by("desc");
 List<Notice> myNoticesList = Arrays.asList(
                                new Notice(noticeId, "This is an important announcement.",
                                                "this is notice descriptions", "General",
                                                "HR Department", "2023-11-01", "2023-11-10", new Date(), userId,
                                                "enable"),
                                new Notice(noticeId, "This is an important announcement.",
                                                "this is notice descriptions", "General",
                                                "HR Department", "2023-11-01", "2023-11-10", new Date(), userId,
                                                status));
     when(noticeRepository.findByStatus(anyString(),any(Sort.class),any(PageRequest.class))).thenReturn(myNoticesList);
   List<Notice>  result = noticeService.noticefindByStatusImportant(status,sort,5);
   assertEquals(myNoticesList, result);
}
}