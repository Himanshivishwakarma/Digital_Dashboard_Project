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

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import java.util.Collections;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class NoticeServiceImplTest {

        @Mock
        private NoticeRepository noticeRepository;

        @InjectMocks
        private NoticeServiceImpl noticeService;
        String userId = "testUser";
        String noticeId = "Important Notice";

        @Mock
        private Cloudinary cloudinary;

        @Mock
        private Uploader uploader;

        @Test
        public void testCreateNoticeByUser() throws IOException {
                // Arrange
                List<String> images = List.of("base64EncodedImage1", "base64EncodedImage2");
                Notice myNotice = new Notice("123", "This is an important announcement.",
                                "this is notice descriptions",
                                "General", "HR Department", "2023-11-01", "2023-11-10", images, new Date(), "John Doe",
                                "enable");

                Map<String, Object> mockUploadResult = Collections.singletonMap("url", "mockImageUrl");
                Mockito.when(uploader.upload(Mockito.any(Object.class), Mockito.anyMap()))
                                .thenReturn(mockUploadResult);

                Mockito.when(cloudinary.uploader()).thenReturn(uploader);

                Mockito.when(noticeRepository.save(any(Notice.class))).thenReturn(myNotice);

                Notice result = noticeService.createNoticeByUser(myNotice);

                assertEquals(myNotice, result);
        }

        @Test
        public void testGetNoticeByNoticeId() {

                Notice myNotice = new Notice(noticeId, "This is an important announcement.",
                                "this is notice descriptions",
                                "General", "HR Department", "2023-11-01", "2023-11-10", null, new Date(), "John Doe",
                                "enable");

                Mockito.when(noticeRepository.findById(eq(noticeId))).thenReturn(Optional.of(myNotice));

                Notice result = noticeService.getNoticeByNoticeId(noticeId);

                assertEquals(myNotice, result);

        }

        @Test
        public void testGetNoticeByUserId() {

                List<Notice> myNotices = Arrays.asList(
                                new Notice(noticeId, "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",
                                                "enable"),
                                new Notice(noticeId, "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",
                                                "enable"));

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
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",
                                                "enable"),
                                new Notice(noticeId, "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",
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
                                                "HR Department", "2023-11-01", "2023-11-10", null, new Date(), userId,
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
      
        List<String> myDepartments = Arrays.asList("HR Department", "IT Department");

        List<Notice> departmentNotices = Arrays.asList(
                new Notice("1", "Notice 1", "Description 1", "HR Department", "Content 1", "2023-11-01", "2023-11-10", null, new Date(), "User1", "enable"),
                new Notice("2", "Notice 2", "Description 2", "IT Department", "Content 2", "2023-11-01", "2023-11-10", null, new Date(), "User2", "enable")
        );

        Page<Notice> departmentNoticesPage = new PageImpl<>(departmentNotices);

        when(noticeRepository.findByDepartmentNameIn(eq(myDepartments), any(Pageable.class)))
                .thenReturn(departmentNoticesPage);

        Page<Notice> result = noticeService.getNoticesByDepartment(myDepartments, PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(departmentNotices, result.getContent());

        verify(noticeRepository, times(1)).findByDepartmentNameIn(eq(myDepartments), any(Pageable.class));

        List<String> allDepartments = Collections.singletonList("All");
        Page<Notice> resultAllDepartments = noticeService.getNoticesByDepartment(allDepartments, PageRequest.of(0, 10));

        assertNull(resultAllDepartments);
    }

        @Test
        public void getAllNoticesSorted() {
                List<Notice> myNoticesList = Arrays.asList(
                                new Notice(noticeId, "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",
                                                "enable"),
                                new Notice(noticeId, "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",
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
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",
                                                "enable"),
                                new Notice(noticeId, "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",
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
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",
                                                "enable"),
                                new Notice(noticeId, "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",
                                                "enable"));
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
        public void testNoticefindByStatusImportant() {
                String status = "enable";
                Sort sort = Sort.by("desc");
                List<Notice> myNoticesList = Arrays.asList(
                                new Notice(noticeId, "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",
                                                "enable"),
                                new Notice(noticeId, "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",
                                                "enable"));
                when(noticeRepository.findByStatus(anyString(), any(Sort.class), any(PageRequest.class)))
                                .thenReturn(myNoticesList);
                List<Notice> result = noticeService.noticefindByStatusImportant(status, sort, 5);
                assertEquals(myNoticesList, result);
        }

        @Test
        public void testUpdateNotice() {
                String noticeId = "1";
                Notice originalNotice = new Notice();
                originalNotice.setNoticeId(noticeId);

                when(noticeRepository.findById(noticeId)).thenReturn(Optional.of(originalNotice));
                when(noticeRepository.save(originalNotice)).thenReturn(originalNotice);

                Notice result = noticeService.updateNotice(originalNotice);

                assertNotNull(result);
                assertEquals(originalNotice, result);

                verify(noticeRepository).findById(noticeId);
                verify(noticeRepository).save(originalNotice);

        }

}