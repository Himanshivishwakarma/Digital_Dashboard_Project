package digital_board.digital_board.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.antlr.v4.runtime.atn.SemanticContext.AND;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import digital_board.digital_board.Dto.CategoryNoticeDto;
import digital_board.digital_board.Dto.NoticeDto;
import digital_board.digital_board.Entity.Notice;
import jakarta.transaction.Transactional;

public interface NoticeRepository extends JpaRepository<Notice, String> {
        @Query("SELECT n FROM Notice n WHERE n.createdBy=:userId")
        Page<Notice> getAllNoticeByUserId(@Param("userId") String userId, Pageable pageable);

        @Query("SELECT n FROM Notice n WHERE n.category IN :category AND (:department IS NULL OR n.departmentName IN :department) AND n.status <> 'disable' AND n.status <> 'completed'")
        Page<Notice> findByCategoryInDepartmentNameInAndStatusNotDisable(List<String> category,
                        @Param("department") List<String> department, Pageable pageable);

        @Query("SELECT n FROM Notice n WHERE n.departmentName IN :departmentName AND (:categories IS NULL OR n.category IN :categories) AND n.status <> 'disable' AND n.status <> 'completed'")
        Page<Notice> findByDepartmentNameInANDcategoriesInAndStatusNotDisable(List<String> departmentName,
                        @Param("categories") List<String> categories, Pageable pageable);

        @Query("SELECT n FROM Notice n WHERE COALESCE(CAST(n.noticeStartDate AS date), CURRENT_DATE) <= CURRENT_DATE " +
                        "AND COALESCE(CAST(n.noticeEndDate AS date), CURRENT_DATE) >= CURRENT_DATE " +
                        "AND n.status <> 'disable' AND n.status <> 'completed'")
        Page<Notice> findAll(Pageable pageable);

        @Query(value = "SELECT * FROM notice WHERE status <> 'disable' AND status <> 'completed'", nativeQuery = true)
        List<Object[]> findAllNotDisabledOrCompleted();

        List<Notice> findByCategoryInAndDepartmentNameIn(List<String> categories, List<String> departmentNames,
                        Pageable pageable);

        @Query(value = "SELECT * FROM digital_board.notice where digital_board.notice.status=:status order by digital_board.notice.notice_created_date desc  LIMIT :limit ;", nativeQuery = true)
        List<Notice> findNoticesWithLimit(@Param("limit") int limit, @Param("status") String status);

        @Query("SELECT n FROM Notice n WHERE n.category IN :categories AND n.status !='disable'")
        List<Notice> findBycategoriesInAndStatusNotDisable(@Param("categories") List<String> categories);

        @Query("SELECT n FROM Notice n WHERE n.departmentName IN :department AND n.status != 'disable'")
        List<Notice> findByDepartmentAndStatusNotDisabled(@Param("department") List<String> department);

        @Query("SELECT n FROM Notice n WHERE n.status !='disable'")
        List<Notice> findAllNotDisabled();

        @Query("SELECT n FROM Notice n WHERE " +
                        "(LOWER(n.noticeTitle) LIKE LOWER(CONCAT('%', :title, '%')) OR " +
                        "LOWER(n.description) LIKE LOWER(CONCAT('%', :description, '%'))) " +
                        "AND n.status <> 'disable' AND n.status <> 'completed'")
        Page<Notice> findByNoticeTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        String title, String description, Pageable pageable);

        Long countByCategory(String category);

        Long countByDepartmentName(String departmentName);

        // get important notice by limit
        List<Notice> findByStatus(String status, Sort sort, PageRequest of);

        List<Notice> findByImportantTrueAndStatusIs(String status, Sort sort, PageRequest of);

        @Query("SELECT n FROM Notice n WHERE (:categories IS NULL OR n.category IN :categories) " +
                        "AND (:departmentNames IS NULL OR n.departmentName IN :departmentNames) " +
                        "AND (:createdBy IS NULL OR n.createdBy IN :createdBy) " +
                        "And n.status <> 'disable' AND n.status <> 'completed'")
        Page<Notice> findByCategoryInAndDepartmentNameInAndAndCreatedByIn(
                        @Param("categories") List<String> categories,
                        @Param("departmentNames") List<String> departmentNames,
                        @Param("createdBy") List<String> createdBy,
                        Pageable pageable);

        @Query("SELECT n FROM Notice n " +
                        "WHERE (:categories IS NULL OR n.category IN :categories) " +
                        "AND (:departmentNames IS NULL OR n.departmentName IN :departmentNames) " +
                        "AND (:createdBy IS NULL OR n.createdBy IN :createdBy) " +
                        "AND (n.important IS NULL OR n.important = true) " +
                        "And n.status <> 'disable' AND n.status <> 'completed'")
        Page<Notice> findByCategoryInAndDepartmentNameInAndStatusInAndCreatedByInAndImportant(
                        @Param("categories") List<String> categories,
                        @Param("departmentNames") List<String> departmentNames,

                        @Param("createdBy") List<String> createdBy,
                        Pageable pageable);

        // @Query("SELECT NEW digital_board.digital_board.Dto.NoticeDto(n.departmentName, COUNT(n)) " +
        //                 "FROM Notice n " +
        //                 "WHERE n.status = 'enable' and department_name <> 'All'" +
        //                 "GROUP BY n.departmentName")
        @Query("SELECT NEW digital_board.digital_board.Dto.NoticeDto(n.departmentName, COUNT(n.noticeId) + COALESCE(allCount, 0))\r\n" + //
                        "FROM Notice n\r\n" + //
                        "LEFT JOIN (\r\n" + //
                        "    SELECT departmentName AS allDepartment, COUNT(noticeId) AS allCount\r\n" + //
                        "    FROM Notice\r\n" + //
                        "    WHERE status = 'enable' AND departmentName = 'All'\r\n" + //
                        "    GROUP BY departmentName\r\n" + //
                        ") AS allNotices\r\n" + //
                        "ON n.departmentName IN ('Beg', 'Meg', 'Iteg')" + //
                        "WHERE n.status = 'enable' AND n.departmentName <> 'All'\r\n" + //
                        "GROUP BY n.departmentName, allCount\r\n" + //
                        "")
                    
        List<NoticeDto> countAllEnableDepartmentNotices();

        @Query("SELECT NEW digital_board.digital_board.Dto.CategoryNoticeDto(n.category, COUNT(n)) " +
                        "FROM Notice n " +
                        "WHERE n.status = 'enable' " +
                        "GROUP BY n.category")
        List<CategoryNoticeDto> countAllEnableCategoryNotices();

        // today created notice count
        @Query(value = "SELECT n FROM Notice n WHERE CAST(n.noticeCreatedDate AS date) = :customDate")
        List<Notice> findByNoticeCreatedDateIsCurrentDate(@Param("customDate") LocalDate customDate);

        // schedule by end date
        // @Query(value = "SELECT n FROM Notice n WHERE CAST(n.noticeCreatedDate AS
        // date) >= current_date")
        // List<Notice> findByNoticeEndDateAfterOrEqual();

        // get notice by department
        @Query("SELECT n FROM Notice n WHERE n.departmentName = :departmentName And CAST(n.noticeCreatedDate AS date) = :customDate And n.status <> 'disable' AND n.status <> 'completed'")
        List<Notice> findByDepartmentNameCustomQuery(@Param("customDate") LocalDate customDate,
                        @Param("departmentName") String departmentName);

        @Query("SELECT n FROM Notice n WHERE n.category = :category  And n.status <> 'disable' AND n.status <> 'completed'")
        List<Notice> findByCategoryName(@Param("category") String category);

        @Query("SELECT NEW digital_board.digital_board.Dto.NoticeDto(n.departmentName, COUNT(n.noticeId)) " +
                        "FROM Notice n " +
                        "JOIN User u ON n.createdBy = u.email " +
                        "WHERE n.status = 'enable' AND u.role = 'SuperAdmin' " +
                        "GROUP BY n.departmentName")
        List<NoticeDto> findNoticeCountsByDepartmentForSuperAdmin();

        // scheduling set completed
        @Transactional
        @Modifying
        // @Query("UPDATE Notice n SET n.status = 'completed' " +
        // "WHERE(COALESCE(CAST(n.noticeEndDate AS date), CURRENT_DATE)<=CURRENT_DATE) "
        // +
        // "AND (n.status = 'completed' OR n.status NOT IN ('disable', 'completed'))")
        @Query("UPDATE Notice n " +
                        "SET n.status = 'completed' " +
                        "WHERE CURRENT_TIMESTAMP >= n.noticeEndDate " +
                        "AND (n.status = 'completed' OR n.status NOT IN ('disable', 'completed'))")
        void updateStatusToCompleted();

      // scheduling set completed
        @Transactional
        @Modifying
        @Query("UPDATE Notice n " +
                        "SET n.status = 'enable' " +
                        "WHERE CURRENT_TIMESTAMP >= n.noticeStartDate " +
                        "AND CURRENT_TIMESTAMP <= n.noticeEndDate " +
                        "AND n.status <> 'enable'")
        void updateStatusForActiveNotices();

}
