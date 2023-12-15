package digital_board.digital_board.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import digital_board.digital_board.Dto.CategoryNoticeDto;
import digital_board.digital_board.Dto.NoticeDto;
import digital_board.digital_board.Entity.Notice;

@ExtendWith(MockitoExtension.class)
public class NoticeRepositoryTest {

        @Mock
        private NoticeRepository noticeRepository;

        @Test
        void testGetAllNoticeByUserId() {
                // Arrange: Create a list of mock notices with different statuses
                List<Notice> mockNotices = List.of(
                  new Notice("1", "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",true,
                                                "enable"),
                                new Notice("1", "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",true,
                                                "disable")              
                );

                // Mock the behavior of the repository method
                when(noticeRepository.getAllNoticeByUserId("testUser", PageRequest.of(0, 10)))
                                .thenReturn(new PageImpl<>(mockNotices));

                // Act: Perform the actual query using the mocked repository
                Page<Notice> resultPage = noticeRepository.getAllNoticeByUserId("testUser", PageRequest.of(0, 10));

                // Assert: Ensure that only notices with status != 'disable' for the given user
                // are returned
                assertEquals(2, resultPage.getTotalElements()); // Assuming two notices have statuses other than
                                                                // 'disable'

                // Additional assertions if needed based on your specific scenario
        }

        @Test
        void testFindByCategoryInDepartmentNameInAndStatusNotDisable() {
                // Arrange: Create a list of mock notices with different categories, department
                // names, and statuses
                List<Notice> mockNotices = Arrays.asList(
                                new Notice("1", "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",true,
                                                "enable"),
                                new Notice("1", "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",true,
                                                "disable"));

                // Mock the behavior of the repository method
                when(noticeRepository.findByCategoryInDepartmentNameInAndStatusNotDisable(
                                Arrays.asList("Category1", "Category2"),
                                Arrays.asList("Department1", "Department2"),
                                Pageable.unpaged())) // You can adjust Pageable as needed
                                .thenReturn(new PageImpl<>(mockNotices));

                // Act: Perform the actual query using the mocked repository
                Page<Notice> resultPage = noticeRepository.findByCategoryInDepartmentNameInAndStatusNotDisable(
                                Arrays.asList("Category1", "Category2"),
                                Arrays.asList("Department1", "Department2"),
                                Pageable.unpaged());

                // Assert: Ensure that only notices meeting the criteria are returned
                assertEquals(2, resultPage.getTotalElements()); // Assuming two notices meet the criteria

                // Additional assertions if needed based on your specific scenario
        }

        @Test
        void testFindByDepartmentNameInANDcategoriesInAndStatusNotDisable() {
                // Arrange: Create a list of mock notices with different department names,
                // categories, and statuses
                List<Notice> mockNotices = Arrays.asList(
                                new Notice("1", "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",true,
                                                "enable"),
                                new Notice("1", "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",true,
                                                "disable"));

                // Mock the behavior of the repository method
                when(noticeRepository.findByDepartmentNameInANDcategoriesInAndStatusNotDisable(
                                Arrays.asList("Department1", "Department2"),
                                Arrays.asList("Category1", "Category2"),
                                Pageable.unpaged())) // You can adjust Pageable as needed
                                .thenReturn(new PageImpl<>(mockNotices));

                // Act: Perform the actual query using the mocked repository
                Page<Notice> resultPage = noticeRepository.findByDepartmentNameInANDcategoriesInAndStatusNotDisable(
                                Arrays.asList("Department1", "Department2"),
                                Arrays.asList("Category1", "Category2"),
                                Pageable.unpaged());

                // Assert: Ensure that only notices meeting the criteria are returned
                assertEquals(2, resultPage.getTotalElements()); // Assuming two notices meet the criteria

                // Additional assertions if needed based on your specific scenario
        }

        @Test
        void testFindAll() {
                List<Notice> mockNotices = Arrays.asList(
                                new Notice("1", "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",true,
                                                "enable"),
                                new Notice("2", "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",true,
                                                "disable"));
                Page<Notice> pageNotices = new PageImpl<>(mockNotices);

                when(noticeRepository.findAll(any(Pageable.class))).thenReturn(pageNotices);

                Page<Notice> result = noticeRepository.findAll(PageRequest.of(0, 10));
                assertEquals(pageNotices, result);
        }

        @Test
        void testFindByCategoryInAndDepartmentNameIn() {
                List<Notice> mockNotices = Arrays.asList(
                                new Notice("1", "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",true,
                                                "enable"),
                                new Notice("2", "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",true,
                                                "disable"));

                when(noticeRepository.findByCategoryInAndDepartmentNameIn(anyList(), anyList(), any(Pageable.class)))
                                .thenReturn(mockNotices);

                noticeRepository.findByCategoryInAndDepartmentNameIn(Arrays.asList("General", "General"),
                                Arrays.asList("HR Department", "HR Department"), PageRequest.of(0, 10));

                assertEquals(mockNotices, mockNotices);

        }

        @Test
        void testFindNoticesWithLimit() {
                List<Notice> mockNotices = Arrays.asList(
                                new Notice("1", "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",true,
                                                "enable"),
                                new Notice("2", "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",true,
                                                "enable"));
                when(noticeRepository.findNoticesWithLimit(anyInt(), anyString())).thenReturn(mockNotices);

                List<Notice> result = noticeRepository.findNoticesWithLimit(1, "enable");
                assertEquals(mockNotices, result);

        }

        @Test
        void testFindBycategoriesInAndStatusNotDisable() {
                List<Notice> mockNotices = Arrays.asList(
                                new Notice("1", "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",true,
                                                "enable"),
                                new Notice("2", "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",true,
                                                "enable"));
                when(noticeRepository.findBycategoriesInAndStatusNotDisable(anyList())).thenReturn(mockNotices);
                List<Notice> result = noticeRepository
                                .findBycategoriesInAndStatusNotDisable(Arrays.asList("General", "General"));
                assertEquals(mockNotices, result);
        }

        @Test
        void TestfindByDepartmentAndStatusNotDisabled() {
                List<Notice> mockNotices = Arrays.asList(
                                new Notice("1", "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",true,
                                                "enable"),
                                new Notice("2", "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",true,
                                                "enable"));
                when(noticeRepository.findBycategoriesInAndStatusNotDisable(anyList())).thenReturn(mockNotices);
                List<Notice> result = noticeRepository
                                .findBycategoriesInAndStatusNotDisable(Arrays.asList("HR Department", "HR Department"));
                assertEquals(mockNotices, result);
        }

        @Test
        void testFindAllNotDisabled() {
                // Arrange: Create a list of mock notices with different statuses
                List<Notice> mockNotices = List.of(
                                new Notice("1", "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",true,
                                                "enable"),
                                new Notice("1", "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",true,
                                                "disable"));

                // Mock the behavior of the repository method
                when(noticeRepository.findAllNotDisabled()).thenReturn(mockNotices);

                // Act: Perform the actual query using the mocked repository
                List<Notice> result = noticeRepository.findAllNotDisabled();

                // Assert: Ensure that only notices with status != 'disable' are returned
                assertEquals(2, result.size()); // Assuming two notices have statuses other than 'disable'

        }

        @Test
        void testFindByNoticeTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase() {
                List<Notice> mockNotices = List.of(
                                new Notice("1", "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",true,
                                                "enable"),
                                new Notice("1", "z",
                                                "z",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",true,
                                                "disable"));
                Page<Notice> pageNotice = new PageImpl<>(mockNotices);

                when(noticeRepository.findByNoticeTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                                anyString(), anyString(), any(Pageable.class))).thenReturn(pageNotice);
                Page<Notice> result = noticeRepository
                                .findByNoticeTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                                                "This is an important announcement.", "this is notice descriptions",
                                                PageRequest.of(0, 10));
                assertEquals(pageNotice, result);
        }

        @Test
        void testCountByCategory() {
                long a = 2;
                List<Notice> mockNotices = List.of(
                                new Notice("1", "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",true,
                                                "enable"),
                                new Notice("1", "z",
                                                "z",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",true,
                                                "disable"));

                when(noticeRepository.countByCategory(anyString())).thenReturn(a);

                Long result = noticeRepository.countByCategory("General");
                assertEquals(a, result);

        }

        @Test
        void testCountByDepartmentName() {
                long a = 2;

                List<Notice> mockNotices = List.of(
                                new Notice("1", "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",true,
                                                "enable"),
                                new Notice("1", "z",
                                                "z",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",true,
                                                "disable"));

                when(noticeRepository.countByCategory(anyString())).thenReturn(a);

                Long result = noticeRepository.countByCategory("General");
                assertEquals(a, result);
        }

        @Test
        void testFindByStatus() {
                List<Notice> mockNotices = List.of(
                                new Notice("1", "This is an important announcement.",
                                                "this is notice descriptions",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",true,
                                                "enable"),
                                new Notice("1", "z",
                                                "z",
                                                "General", "HR Department", "2023-11-01", "2023-11-10", null,
                                                new Date(), "John Doe",true,
                                                "disable"));

                when(noticeRepository.findByStatus(anyString(), any(Sort.class), any(PageRequest.class)))
                                .thenReturn(mockNotices);
                List<Notice> result = noticeRepository.findByStatus("enable", Sort.by("desc"), PageRequest.of(0, 10));
                assertEquals(mockNotices, result);
        }

        // @Test
        // void testFindByCategoryInAndDepartmentNameInAndStatusInAndCreatedByIn() {
        //         List<Notice> mockNotices = List.of(
        //                         new Notice("1", "This is an important announcement.",
        //                                         "this is notice descriptions",
        //                                         "General", "HR Department", "2023-11-01", "2023-11-10", null,
        //                                         new Date(), "John Doe",true,
        //                                         "enable"),
        //                         new Notice("1", "z",
        //                                         "z",
        //                                         "General", "HR Department", "2023-11-01", "2023-11-10", null,
        //                                         new Date(), "John Doe",true,
        //                                         "disable"));
        //         Page<Notice> pageNotice = new PageImpl<>(mockNotices);
        //         when(noticeRepository.findByCategoryInAndDepartmentNameInAndStatusInAndCreatedByIn(anyList(), anyList(),
        //                         anyList(), anyList(), any(Pageable.class))).thenReturn(pageNotice);
        //         Page<Notice> result = noticeRepository.findByCategoryInAndDepartmentNameInAndStatusInAndCreatedByIn(
        //                         Arrays.asList("categories", "categories"), Arrays.asList("categories", "categories"),
        //                         Arrays.asList("categories", "categories"), Arrays.asList("categories", "categories"),
        //                         PageRequest.of(0, 10));
        //         assertEquals(pageNotice, result);
        // }

        @Test
        void testCountAllEnableDepartmentNotices() {
                long count = 2;
                List<NoticeDto> mockNotices = Arrays.asList(new NoticeDto("Iteg", count),
                                new NoticeDto("Iteg", count));

                when(noticeRepository.countAllEnableDepartmentNotices()).thenReturn(mockNotices);
                List<NoticeDto> result = noticeRepository.countAllEnableDepartmentNotices();
                assertEquals(mockNotices, result);
        }

         @Test
        void testCountAllEnableCategoryNotices() {
                long count = 2;
                List<CategoryNoticeDto> mockNotices = Arrays.asList(new CategoryNoticeDto("Iteg", count),
                                new CategoryNoticeDto("Iteg", count));

                when(noticeRepository.countAllEnableCategoryNotices()).thenReturn(mockNotices);
                
                List<CategoryNoticeDto> result = noticeRepository.countAllEnableCategoryNotices();
                assertEquals(mockNotices, result);
        }

}
